package com.capstone.mountain.repository;

import com.capstone.mountain.dto.MountainMainPageDto;
import com.capstone.mountain.dto.MountainNearDto;
import com.capstone.mountain.dto.MountainPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface MountainRepositoryCustom {
    List<MountainMainPageDto> getHotMountain();
    List<MountainNearDto> getNearMountain(double latitude, double longitude);
    Page<MountainPreviewDto> getMountainList(Pageable pageable);
}
