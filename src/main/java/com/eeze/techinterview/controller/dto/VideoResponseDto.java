package com.eeze.techinterview.controller.dto;

import com.eeze.techinterview.domain.Video;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponseDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
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
