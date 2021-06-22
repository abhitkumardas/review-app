package com.tchyon.reviewapp.controller;

import com.tchyon.reviewapp.model.Platform;
import com.tchyon.reviewapp.model.request.PlatformDto;
import com.tchyon.reviewapp.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;


    @PostMapping
    public ResponseEntity addPlatform(@RequestBody PlatformDto platformDto){
        return ResponseEntity.ok(platformService.addPlatform(platformDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity getAll(@PathVariable Long id){
        return ResponseEntity.ok(platformService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(platformService.findAll());
    }




    @PostMapping(value = "/admin")
    public ResponseEntity addPlatformAdmin(@RequestBody Platform platform, @RequestHeader Long userId){
        return ResponseEntity.ok(platformService.addPlatformAdmin(platform, userId));
    }

    @PutMapping
    public ResponseEntity updatePlatform(@RequestBody Platform platform, @RequestHeader Long userId){
        return ResponseEntity.ok(platformService.updatePlatform(platform, userId));
    }
}
