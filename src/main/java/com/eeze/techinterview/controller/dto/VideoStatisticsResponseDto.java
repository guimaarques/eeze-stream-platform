package com.eeze.techinterview.controller.dto;

import com.eeze.techinterview.domain.Video;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatisticsResponseDto {

    @JsonProperty("views")
    private int views;

    @JsonProperty("impressions")
    private int impressions;

    public static VideoStatisticsResponseDto of(Video video) {
        return VideoStatisticsResponseDto.builder()
                .views(video.getViews())
                .impressions(video.getImpressions())
                .build();
    }

}
