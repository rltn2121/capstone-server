package com.capstone.mountain.service;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.MountainMainPageDto;
import com.capstone.mountain.dto.MountainPreviewDto;
import com.capstone.mountain.repository.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return mountainRepository.getNearMountain(latitude, longitude);
    }

    public Page<MountainPreviewDto> getMountainList(Pageable pageable){
        return mountainRepository.getMountainList(pageable);
    }
}
