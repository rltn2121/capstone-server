package com.capstone.mountain.repository;

import com.capstone.mountain.dto.MountainPreviewDto;

import java.util.List;

public interface MountainRepositoryCustom {
    List<MountainPreviewDto> getHotMountain();
    List<MountainPreviewDto> getNearMountain(double latitude, double longitude);
}
