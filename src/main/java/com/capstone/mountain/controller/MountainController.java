package com.capstone.mountain.controller;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MountainController {
    private final MountainService mountainService;

//    @GetMapping("/search")
//    public List<Mountain> findByName(@RequestParam String name){
//        return mountainService.findByName(name);
//    }

    @GetMapping("/mountain/{mountain_id}")
    public Optional<Mountain> findById(@PathVariable("mountain_id") Long id){
        return mountainService.findById(id);
    }
}
