package com.example.google_vision.controller;

import com.example.google_vision.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VisionController {

    @Autowired
    VisionService visionService;

    @PostMapping("/count.face")
    public ResponseEntity<Integer> countFace(@RequestParam("image")MultipartFile file) throws Exception {
        return ResponseEntity.ok(visionService.countFace(file));
    }
}
