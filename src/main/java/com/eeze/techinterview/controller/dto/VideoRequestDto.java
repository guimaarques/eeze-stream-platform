package com.eeze.techinterview.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoRequestDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("synopisis")
    private String synopisis;

    @JsonProperty("director")
    private String director;

    @JsonProperty("cast")
    private String cast;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("running_time")
    private String runningTime;

}
