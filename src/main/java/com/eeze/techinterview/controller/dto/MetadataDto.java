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
public class MetadataDto {

    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonProperty("synopisis")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String synopisis;

    @JsonProperty("director")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String director;

    @JsonProperty("cast")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cast;

    @JsonProperty("release_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String releaseDate;

    @JsonProperty("genre")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String genre;

    @JsonProperty("running_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String runningTime;

    public static MetadataDto of(Video video) {
        return MetadataDto.builder()
                .title(video.getTitle())
                .synopisis(video.getSynopisis())
                .director(video.getDirector())
                .cast(video.getVideoCast())
                .releaseDate(video.getReleaseDate())
                .genre(video.getGenre())
                .runningTime(video.getRunningTime())
                .build();

    }
}
