package com.capstone.mountain.service;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.MountainMainPageDto;
import com.capstone.mountain.dto.MountainNearDto;
import com.capstone.mountain.dto.MountainPreviewDto;
import com.capstone.mountain.repository.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MountainService {
    private final MountainRepository mountainRepository;

    public List<Mountain> findByName(String name){
        return mountainRepository.findByName(name);
    }

    public Optional<Mountain> findById(Long id) {
        return mountainRepository.findById(id);
    }

    public List<MountainMainPageDto> getHotMountain(){
        return mountainRepository.getHotMountain();
    }

    public List<MountainMainPageDto> getNearMountain(double latitude, double longitude){
        List<MountainNearDto> mountains = mountainRepository.getNearMountain(latitude, longitude);

        List<MountainMainPageDto> nearMountains = new ArrayList<>();
        for (MountainNearDto mountain : mountains) {
            double dstLat = mountain.getLatitude();
            double dstLon = mountain.getLongitude();
            double dist = getDistByLatLon(latitude, longitude, dstLat, dstLon);
            if(dist < 50000)
                nearMountains.add(new MountainMainPageDto(mountain.getThumbnail(), mountain.getId(), mountain.getName()));
        }
        return nearMountains;
    }

    public Page<MountainPreviewDto> getMountainList(Pageable pageable){
        return mountainRepository.getMountainList(pageable);
    }

    private double getDistByLatLon(double lat1, double lon1, double lat2, double lon2){
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
    }
}
