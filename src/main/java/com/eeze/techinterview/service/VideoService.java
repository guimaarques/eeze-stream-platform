package com.eeze.techinterview.service;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.dto.VideoResponseDto;
import com.eeze.techinterview.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    Video getVideo(String name);

    void saveVideo(MultipartFile file, String name) throws IOException;

    List<VideoResponseDto> getAllVideosNames();

    VideoResponseDto getVideoMetadatasByName(String name);

    VideoResponseDto addMetadata(String name, VideoRequestDto request) throws IOException;

    void deListVideo(String name) throws IOException;
}
