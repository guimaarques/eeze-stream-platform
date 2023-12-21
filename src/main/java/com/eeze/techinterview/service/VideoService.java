package com.eeze.techinterview.service;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.enums.StatisticMetricEnum;
import com.eeze.techinterview.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    Video getVideo(String name);

    Video saveVideo(MultipartFile file, String name);

    List<Video> getAllVideosNames();

    Video addMetadata(String name, VideoRequestDto request);

    void deListVideo(String name);

    List<Video> getVideosByQuery(Object field, Object value);

    void increaseVideoStatistic(String name, StatisticMetricEnum statisticMetricEnum);

}
