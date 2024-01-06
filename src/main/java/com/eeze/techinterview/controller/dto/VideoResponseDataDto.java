package com.eeze.techinterview.controller.dto;

import com.eeze.techinterview.domain.Video;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponseDataDto {

    @JsonProperty("data")
    private List<VideoResponseDto> data;

    public static VideoResponseDataDto of(Collection<Video> videos, boolean content) {
        List<VideoResponseDto> videosResponse = new ArrayList<>();

        for (Video video : videos) {
            videosResponse.add(VideoResponseDto.of(video, content));
        }
        return VideoResponseDataDto
                .builder()
                .data(videosResponse)
                .build();

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoResponseDto {

        @JsonProperty("name")
        private String name;

        @JsonProperty("content")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private byte[] video;

        @JsonProperty("metadata")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private MetadataDto metadataDto;


        public static VideoResponseDto of(Video video, boolean content) {
            MetadataDto metadata = MetadataDto.of(video);

            return VideoResponseDto.builder()
                    .name(video.getName())
                    .video(content ? video.getData() : null)
                    .metadataDto(metadata)
                    .build();

        }
    }
}
