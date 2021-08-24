package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.module.model.dto.MountainMainPageDto;
import com.capstone.mountain.module.model.dto.MountainPreviewDto;
import com.capstone.mountain.module.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mountain")
public class MountainController {
    private final MountainService mountainService;

    /**
     * 기능: 인기 산 목록 조회
     * @return 인기 산 목록
     */
    @GetMapping("/hot")
    public ResponseEntity<Message> getHotMountain(Pageable pageable){
        Page<MountainPreviewDto> result = mountainService.getMountainList(pageable);
        Message message = new Message(OK, "조회 성공", result);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 근처 산 목록 조회
     * @return 근처 산 목록
     */
    @GetMapping("/near")
    public ResponseEntity<Message> getNearMountain(@RequestParam double latitude,
                                                   @RequestParam double longitude){
        List<MountainMainPageDto> result = mountainService.getNearMountain(latitude, longitude);
        Message message = new Message(OK, "조회 성공", result);
        return new ResponseEntity<>(message, OK);
    }
}
