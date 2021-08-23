# -*- coding: utf-8 -*-
import json
import os.path
import sys as mod_sys
import math as mod_math
import logging as mod_logging
import urllib as mod_urllib
import os as mod_os
import gpxpy as mod_gpxpy
from PIL import Image as mod_pil_image
from PIL import ImageDraw as mod_pil_draw
import glob as mod_glob
import openpyxl
import googlemaps
import re

osm_cache_base = r"c:\devel-python\.cache\opencyclemap"
osm_tile_res = 256


def format_time(time_s):
    if not time_s:
        return 'n/a'
    minutes = mod_math.floor(time_s / 60.)
    hours = mod_math.floor(minutes / 60.)
    return '%s:%s:%s' % (str(int(hours)).zfill(2), str(int(minutes % 60)).zfill(2), str(int(time_s % 60)).zfill(2))


def get_tile_url(x, y, z):
    return "http://a.tile.thunderforest.com/atlas/%d/%d/%d.png?apikey=051f8fe137f6465793bac4424bd4145b" % (z, x, y)


def get_tile_filename(x, y, z):
    return r"c:\devel-python\.cache\opencyclemap\%d\%d\%d.png" % (z, x, y)


def get_map_suffix():
    return "osm-cycle"


def osm_lat_lon_to_x_y_tile(lat_deg, lon_deg, zoom):
    """ Gets tile containing given coordinate at given zoom level """
    # taken from http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames, works for OSM maps and mapy.cz
    lat_rad = mod_math.radians(lat_deg)
    n = 2.0 ** zoom
    xtile = int((lon_deg + 180.0) / 360.0 * n)
    ytile = int((1.0 - mod_math.log(mod_math.tan(lat_rad) + (1 / mod_math.cos(lat_rad))) / mod_math.pi) / 2.0 * n)
    return (xtile, ytile)


def osm_get_auto_zoom_level(min_lat, max_lat, min_lon, max_lon, max_n_tiles):
    """ Gets zoom level which contains at maximum `max_n_tiles` """
    for z in range(0, 17):
        x1, y1 = osm_lat_lon_to_x_y_tile(min_lat, min_lon, z)
        x2, y2 = osm_lat_lon_to_x_y_tile(max_lat, max_lon, z)
        max_tiles = max(abs(x2 - x1), abs(y2 - y1))
        if (max_tiles > max_n_tiles):
            print("Max tiles: %d" % max_tiles)
            return z
    return 17


def osm_cache_tile(x, y, z):
    """ Downloads tile x,y,x into cache. Directories are automatically created, existing files are not retrieved. """
    src_url = get_tile_url(x, y, z)
    dst_filename = get_tile_filename(x, y, z)

    dst_dir = mod_os.path.dirname(dst_filename)
    if not mod_os.path.exists(dst_dir):
        mod_os.makedirs(dst_dir)
    if mod_os.path.isfile(dst_filename):
        return

    print("Downloading %s ..." % src_url)
    request = mod_urllib.request.Request(src_url)
    response = mod_urllib.request.urlopen(request)
    data = response.read()
    f = open(dst_filename, "wb")
    f.write(data)
    f.close()


class MapCreator:
    """ Class for map drawing """

    def __init__(self, min_lat, max_lat, min_lon, max_lon, z):
        """ constructor """
        x1, y1 = osm_lat_lon_to_x_y_tile(min_lat, min_lon, z)
        x2, y2 = osm_lat_lon_to_x_y_tile(max_lat, max_lon, z)
        x_gap = abs(x1 - x2)
        y_gap = abs(y1 - y2)
        self.x1 = min(x1, x2)
        self.x2 = max(x1, x2)
        self.y1 = min(y1, y2)
        self.y2 = max(y1, y2)
        self.w = (self.x2 - self.x1 + 1) * osm_tile_res
        self.h = (self.y2 - self.y1 + 1) * osm_tile_res
        self.z = z
        print(self.w, self.h)
        self.dst_img = mod_pil_image.new("RGB", (self.w, self.h))

    def cache_area(self):
        """ Downloads necessary tiles to cache """
        print("Caching tiles x1=%d y1=%d x2=%d y2=%d" % (self.x1, self.y1, self.x2, self.y2))
        for y in range(self.y1, self.y2 + 1):
            for x in range(self.x1, self.x2 + 1):
                osm_cache_tile(x, y, self.z)

    def create_area_background(self):
        """ Creates background map from cached tiles """
        for y in range(self.y1, self.y2 + 1):
            for x in range(self.x1, self.x2 + 1):
                try:
                    src_img = mod_pil_image.open(get_tile_filename(x, y, z))
                    dst_x = (x - self.x1) * osm_tile_res
                    dst_y = (y - self.y1) * osm_tile_res
                    self.dst_img.paste(src_img, (dst_x, dst_y))
                except Exception as e:
                    print("Error processing file " + get_tile_filename(x, y, z))

    def lat_lon_to_image_xy(self, lat_deg, lon_deg):
        """ Internal. Converts lat, lon into dst_img coordinates in pixels """
        lat_rad = mod_math.radians(lat_deg)
        n = 2.0 ** self.z
        xtile_frac = (lon_deg + 180.0) / 360.0 * n
        ytile_frac = (1.0 - mod_math.log(mod_math.tan(lat_rad) + (1 / mod_math.cos(lat_rad))) / mod_math.pi) / 2.0 * n
        img_x = int((xtile_frac - self.x1) * osm_tile_res)
        img_y = int((ytile_frac - self.y1) * osm_tile_res)
        return (img_x, img_y)

    def draw_track(self, gpx):
        """ Draw GPX track onto map """
        draw = mod_pil_draw.Draw(self.dst_img)
        trk = 0  # Just changes color of segment a little
        for track in gpx.tracks:
            for segment in track.segments:
                idx = 0
                x_from = 0
                y_from = 0
                for point in segment.points:
                    if (idx == 0):
                        x_from, y_from = self.lat_lon_to_image_xy(point.latitude, point.longitude)
                    else:
                        x_to, y_to = self.lat_lon_to_image_xy(point.latitude, point.longitude)
                        #                        draw.line ((x_from,y_from,x_to,y_to), (255,0,trk), 2)
                        draw.line((x_from, y_from, x_to, y_to), (255, 0, 0), 3)
                        x_from = x_to
                        y_from = y_to
                    idx += 1
                trk += 32
                if (trk > 160):
                    trk = 0

    def save_image(self, filename):
        print("Saving " + filename)
        self.dst_img.save(filename)


if (__name__ == '__main__'):
    gpx_files = mod_glob.glob(r"C:\Users\JuhoNote\PycharmProjects\GpxReader\gpx\*.gpx")
    gmaps = googlemaps.Client(key='AIzaSyD3UmpeZA6kBNTuExAgRkA55I9bqhV1Rpo')
    wb = openpyxl.Workbook()
    sheet = wb.active
    sheet.append(
        ["gpx_id", "file_name", "distance", "moving_time_str", "total_time_str", "moving_time_sec", "total_time_sec",
         "avg_speed", "avg_pace", "address", "lat", "lon", "max_ele", "min_ele", "ele_dif", "uphill", "downhill",
         "difficulty", "record_time"])

    result = []
    res_idx = 0
    for gpx_file in gpx_files:
        try:
            gpx = mod_gpxpy.parse(open(gpx_file, encoding='UTF8'))

            # Print some track stats
            print('--------------------------------------------------------------------------------')
            print('  GPX file     : %s' % gpx_file)
            print('  GPX id       : %s' % os.path.basename(gpx_file).split("_")[0])
            start_time, end_time = gpx.get_time_bounds()
            print('  Started       : %s' % start_time)
            print('  Ended         : %s' % end_time)
            distance = gpx.length_3d() / 1000.
            str_distance = f'{distance:.2f}'
            print('  Length        : %s' % str_distance + "km")
            moving_time, stopped_time, moving_distance, stopped_distance, max_speed = gpx.get_moving_data()
            total_time = moving_time + stopped_time
            print('  Moving time o : %s' % moving_time)
            print('  Total time  o : %s' % total_time)
            print('  Moving time   : %s' % format_time(moving_time))
            print('  Stopped time  : %s' % format_time(stopped_time))
            print('  Total time    : %s' % format_time(total_time))
            if start_time is None or moving_time is None or moving_time == 0:
                os.remove(gpx_file)
                continue
            avg_speed = distance / moving_time * 3600
            avg_pace = moving_time / 60. / distance
            moving_time_h = moving_time / 3600
            total_time_h = total_time / 3600
            if avg_speed > 8 or moving_time_h > 100 or total_time_h > 200:
                os.remove(gpx_file)
                continue
            str_avg_speed = f'{avg_speed:.2f}'
            str_avg_pace = f'{avg_pace:.2f}'
            print('  Avg speed     : %s' % str_avg_speed + "km/h")
            print('  Avg pace      : %s' % str_avg_pace + "min/km")

            # 주소
            if len(gpx.get_location_at(start_time)) != 0 and not None:
                mylocation = gpx.get_location_at(start_time)[0]
            else:
                os.remove(gpx_file)
                continue
            mylat = mylocation.latitude
            mylon = mylocation.longitude
            reverse_geocode_result = gmaps.reverse_geocode((mylat, mylon), language='ko')
            tmpdata = json.dumps(reverse_geocode_result[0])
            data = json.loads(tmpdata)
            address = re.sub("대한민국 |대한민국", "", data["formatted_address"])
            print('  get location  : %s' % address)
            print('  Max speed     : %2.2fm/s = %2.2fkm/h' % (max_speed, max_speed * 60. ** 2 / 1000.))
            min_ele, max_ele = gpx.get_elevation_extremes()
            if max_ele is None or min_ele is None:
                os.remove(gpx_file)
                continue
            ele_dif = max_ele - min_ele
            if max_ele > 3000 or min_ele < -1000:
                os.remove(gpx_file)
                continue
            str_max_height = f'{max_ele:.0f}'
            print('  Max height    : %s' % str_max_height + "m")
            min_height = f'{min_ele:.0f}'
            print('  Min height    : %s' % min_height + "m")
            uphill, downhill = gpx.get_uphill_downhill()
            if uphill > 30000:
                os.remove(gpx_file)
                continue
            str_uphill = f'{uphill:.0f}'
            str_downhill = f'{downhill:.0f}'
            print('  Total uphill  : %s' % str_uphill + "m")
            print('  Total downhill: %s' % str_downhill + "m")

            # 난이도 계산
            score = 0
            ds = 0
            es = 0
            ss = 0
            if distance < 5:
                ds = 1
            elif 5 <= distance < 10:
                ds = 2
            elif 10 <= distance < 15:
                ds = 3
            elif 15 <= distance < 20:
                ds = 4
            else:
                ds = 5
            if ele_dif < 300:
                es = 1
            elif 300 <= ele_dif < 500:
                es = 2
            elif 500 <= ele_dif < 700:
                es = 3
            elif 700 <= ele_dif < 850:
                es = 4
            else:
                es = 5
            if avg_speed < 3:
                ss = 5
            elif 3 <= avg_speed < 3.3:
                ss = 4
            elif 3.3 <= avg_speed < 3.7:
                ss = 3
            elif 3.7 <= avg_speed < 4:
                ss = 2
            else:
                ss = 1
            score = ds * 0.3 + es * 0.3 + ss * 0.4

            difficulty = ""
            if score < 3:
                difficulty = "하"
            elif 3 <= score < 4:
                difficulty = "중"
            else:
                difficulty = "상"

            # result 배열에 추가
            result.append([])
            result[res_idx].append(int(os.path.basename(gpx_file).split("_")[0]))  # gpx_id
            result[res_idx].append(os.path.basename(gpx_file))  # filename
            result[res_idx].append(round(distance, 2))  # distance
            result[res_idx].append(format_time(moving_time))  # moving time str
            result[res_idx].append(format_time(total_time))  # total time str
            result[res_idx].append(round(moving_time, 0))  # moving time sec
            result[res_idx].append(round(total_time, 0))  # total time sec
            result[res_idx].append(round(avg_speed, 2))  # avg speed
            result[res_idx].append(round(avg_pace, 2))  # avg pace
            result[res_idx].append(address)  # address
            result[res_idx].append(mylat)  # lat
            result[res_idx].append(mylon)  # lon
            result[res_idx].append(round(max_ele, 0))  # max height
            result[res_idx].append(round(min_ele, 0))  # min height
            result[res_idx].append(round(ele_dif, 0))  # ele difference
            result[res_idx].append(round(uphill, 0))  # uphill
            result[res_idx].append(round(downhill, 0))  # downhill
            result[res_idx].append(difficulty)  # 난이도 null
            str_end_time = f'{end_time}'
            result[res_idx].append(str_end_time.split("+")[0])  # record time

            # csv에 result 추가
            sheet.append(result[res_idx])
            res_idx += 1

            min_lat, max_lat, min_lon, max_lon = gpx.get_bounds()
            print("min_lat = ", min_lat)
            print("max_lat = ", max_lat)
            print("min_lon = ", min_lon)
            print("max_lon = ", max_lon)

            print("  Bounds        : [%1.4f,%1.4f,%1.4f,%1.4f]" % (min_lat, max_lat, min_lon, max_lon))

            w = 5
            h = 8
            origin_rate = h / w  # 원본 비율

            w_gap = (max_lat - min_lat)
            h_gap = (max_lon - min_lon)

            now_rate = h_gap / w_gap  # 현재 비율

            if (now_rate > origin_rate):
                print("x 늘려야 함")
                t = 0.5 * ((h_gap / origin_rate) - w_gap)
                min_lat = min_lat - t
                max_lat = max_lat + t

            elif (now_rate < origin_rate):
                print("y 늘려야 함")
                t = 0.5 * w_gap * (origin_rate - now_rate)
                min_lon = min_lon - t
                max_lon = max_lon + t

            print("------ changed ------")
            print("min_lat = ", min_lat)
            print("max_lat = ", max_lat)
            print("min_lon = ", min_lon)
            print("max_lon = ", max_lon)

            min_lat = min_lat - 0.003
            max_lat = max_lat + 0.003
            z = osm_get_auto_zoom_level(min_lat, max_lat, min_lon, max_lon, 1)
            print("  Zoom Level    : %d" % z)

            # Create the map
            map_creator = MapCreator(min_lat, max_lat, min_lon - 0.03, max_lon + 0.03, z)
            map_creator.cache_area()
            map_creator.create_area_background()
            map_creator.draw_track(gpx)
            map_creator.save_image(gpx_file[:-4] + '.png')


        except Exception as e:
            mod_logging.exception(e)
            print('Error processing %s' % gpx_file)
            mod_sys.exit(1)

    wb.save("C:\\Users\\JuhoNote\\PycharmProjects\\GpxReader\\gpx_data.csv")
    wb.close()
