package com.eeze.techinterview.controller;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.dto.VideoResponseDataDto;
import com.eeze.techinterview.controller.dto.VideoStatisticsResponseDto;
import com.eeze.techinterview.controller.enums.StatisticMetricEnum;
import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.service.VideoService;
import com.eeze.techinterview.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/eeze-platform")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/videos")
    public ResponseEntity<VideoResponseDataDto> saveVideo(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("name") String name) {
        Video video = videoService.saveVideo(file, name);
        return ResponseEntity.ok(VideoResponseDataDto.of(Arrays.asList(video), false));

    }

    @PutMapping("/videos/{name}")
    public ResponseEntity<VideoResponseDataDto> updateVideo(@PathVariable("name") String name,
                                                            @RequestBody VideoRequestDto videoRequestDto) {

        final Video video = videoService.addMetadata(name, videoRequestDto);
        return ResponseEntity
                .ok(VideoResponseDataDto.of(Arrays.asList(video), false));
    }

    @GetMapping("/videos/{name}/play")
    public ResponseEntity<Resource> getVideoByName(@PathVariable("name") String name) {

        videoService.increaseVideoStatistic(name, StatisticMetricEnum.VIEWS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(videoService.getVideo(name).getData()));

    }

    @GetMapping("/videos/{name}")
    public ResponseEntity<VideoResponseDataDto> getVideoAndItsMetadataByName(@PathVariable("name") String name,
                                                                             @RequestParam(value = "videoContent", required = false) boolean videoContent) {
        Video video = videoService.getVideo(name);
        if(videoContent) videoService.increaseVideoStatistic(name, StatisticMetricEnum.IMPRESSIONS);
        return ResponseEntity
                .ok(VideoResponseDataDto.of(Arrays.asList(video), videoContent));
    }

    @GetMapping("/videos")
    public ResponseEntity<VideoResponseDataDto> getVideos() {
        List<Video> allVideosNames = videoService.getAllVideosNames();

        return ResponseEntity.ok(VideoResponseDataDto.of(allVideosNames, false));
    }

    @GetMapping("/videos/{name}/statistics")
    public ResponseEntity<VideoStatisticsResponseDto> getStatistics(@PathVariable("name") String name){

        Video video = videoService.getVideo(name);
        return ResponseEntity.ok(VideoStatisticsResponseDto.of(video));
    }

    @GetMapping("/videos/query")
    public ResponseEntity<List<VideoResponseDataDto>> getVideosByQuery(@RequestParam(value = "field") Object field,
                                                                       @RequestParam(value = "value") Object value) {
        QueryUtil.setField(field.toString());
        List<VideoResponseDataDto> response = new ArrayList<>();

        videoService.getVideosByQuery(field, value)
                .forEach(video -> response.add(VideoResponseDataDto.of(Arrays.asList(video), false)));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/videos/{name}")
    public void deleteVideo(@PathVariable("name") String name) {

        videoService.deListVideo(name);
        ResponseEntity.ok();
    }
}