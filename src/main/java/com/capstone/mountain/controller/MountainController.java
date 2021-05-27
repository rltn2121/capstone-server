package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.dto.MountainMainPageDto;
import com.capstone.mountain.dto.MountainPreviewDto;
import com.capstone.mountain.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class MountainController {
    private final MountainService mountainService;

//    @GetMapping("/search")
//    public List<Mountain> findByName(@RequestParam String name){
//        return mountainService.findByName(name);
//    }

//    @GetMapping("/mountain/{mountain_id}")
//    public Optional<Mountain> findById(@PathVariable("mountain_id") Long id){
//        return mountainService.findById(id);
//    }
//
    @GetMapping("/mountain/hot")
    public ResponseEntity<Message> getHotMountain(Pageable pageable){
        Page<MountainPreviewDto> result = mountainService.getMountainList(pageable);

        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(result);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/mountain/near")
    public ResponseEntity<Message> getNearMountain(){
        double latitude = 34.976956653660075;
        double longitude = 128.33237146155233;
        List<MountainMainPageDto> result = mountainService.getNearMountain(latitude, longitude);

        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(result);
        return new ResponseEntity<>(message, OK);
    }
}
