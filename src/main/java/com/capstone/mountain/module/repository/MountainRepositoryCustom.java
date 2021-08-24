package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.dto.MountainMainPageDto;
import com.capstone.mountain.module.model.dto.MountainNearDto;
import com.capstone.mountain.module.model.dto.MountainPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface MountainRepositoryCustom {
    List<MountainMainPageDto> getHotMountain();
    List<MountainNearDto> getNearMountain(double latitude, double longitude);
    Page<MountainPreviewDto> getMountainList(Pageable pageable);
}
