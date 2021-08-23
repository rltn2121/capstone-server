from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import urllib.request
import bs4
import openpyxl
import os
import shutil

driver = webdriver.Chrome()
driver.get("http://www.everytrail.co.kr/")
time.sleep(1)
elem = driver.find_element_by_name("id")
time.sleep(1)
elem.send_keys("jooho297")  #에브리트레일 id
elem = driver.find_element_by_name("member_pw")
time.sleep(1)
elem.send_keys("jh09121015")  #에브리트레일 pw
elem.send_keys(Keys.RETURN)


headers = {'User-Agent': 'Chrome/66.0.3359.181'}
wb = openpyxl.Workbook()
sheet = wb.active
sheet.append(
    ["gpx_id", "링크", "제목", "난이도", "거리", "시간", "주소", "이동시간", "최고", "최저", "오르막합", "내리막합", "평속", "이동평속", "조회", "기록시각", "등록시각", "by",
     "닉네임", "user_id"])

users = {}
user_id = 0
total_id = 1
# range 1 to 454
for i in range(1, 454):
    print("********** page " + str(i) + " **********")
    url = "http://www.everytrail.co.kr/gpssearch.trail?order=latest&act_cd=40&area_cd=&diff_cd=&duration_cd=&query=&gps_photo_yn=&cat_cd=&page=" + str(i)
    req = urllib.request.Request(url, headers=headers)
    html = urllib.request.urlopen(req)

    bs_obj = bs4.BeautifulSoup(html, "html.parser")

    div_article = bs_obj.findAll("div", {"class": "article_text"})

    dwDir = "C:\\Sangallae\\Downloads"
    svDir = "C:\\Sangallae\\Downloads\\save"
    result = []
    res_idx = 0
    for divs in div_article:
        result.append([])
        result[res_idx].append(total_id)
        url = divs.find("a")['href']
        result[res_idx].append(url)
        dwUrl = "http://www.everytrail.co.kr" + url.replace("detail", "download")
        print(dwUrl)
        driver.get(dwUrl)
        time.sleep(2)
        file_names = os.listdir(dwDir)
        for name in file_names:
            src = os.path.join(dwDir, name)
            dst = os.path.join(dwDir, str(total_id) + "_" + name)
            print(str(total_id) + "_" + name)
            os.rename(src, dst)
            shutil.move(dst, svDir)


        spans = divs.findAll("span")

        idx = 0
        for span in spans:
            if span.text == '|':
                continue
            idx += 1
            result[res_idx].append(span.text)
            if idx == 17:
                if span.text in users:
                    result[res_idx].append(users[span.text])
                else:
                    users[span.text] = user_id
                    result[res_idx].append(user_id)
                    user_id += 1
        sheet.append(result[res_idx])
        res_idx = res_idx + 1
        total_id += 1

# print(result)

wb.save("C:\\Sangallae\\gpx_data.csv")


driver.close()
