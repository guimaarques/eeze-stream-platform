package com.eeze.techinterview.controller;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.dto.VideoResponseDto;
import com.eeze.techinterview.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping()
    public ResponseEntity<String> saveVideo(@RequestParam("file") MultipartFile file,
                                            @RequestParam("name") String name) throws IOException {
        videoService.saveVideo(file, name);
        return ResponseEntity.ok("Video saved successfully.");

    }

    @PutMapping("/{name}")
    public ResponseEntity<VideoResponseDto> updateVideo(@PathVariable("name") String name,
                                                        @RequestBody VideoRequestDto videoRequestDto) throws IOException{

        return ResponseEntity
                .ok(videoService.addMetadata(name, videoRequestDto));
    }

    @GetMapping("/{name}/play")
    public ResponseEntity<Resource> getVideoByName(@PathVariable("name") String name){

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(videoService.getVideo(name).getData()));

    }

    @GetMapping("/{name}")
    public ResponseEntity<VideoResponseDto> getVideoAndItsMetadataByName(@PathVariable("name") String name){
        return ResponseEntity
                .ok(videoService.getVideoMetadatasByName(name));
    }

    @GetMapping()
    public ResponseEntity<List<VideoResponseDto>> getVideos(){
        return ResponseEntity
                .ok(videoService.getAllVideosNames());

    }

    @DeleteMapping("/{name}")
    public void deleteVideo(@PathVariable("name") String name) throws IOException {

        videoService.deListVideo(name);
        ResponseEntity.ok();
    }
}