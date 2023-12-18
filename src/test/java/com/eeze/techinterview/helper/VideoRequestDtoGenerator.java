package com.eeze.techinterview.helper;

import com.eeze.techinterview.controller.dto.VideoRequestDto;

public final class VideoRequestDtoGenerator {

    public static VideoRequestDto videoRequestDto() {
        return VideoRequestDto.builder()
                .title("Title")
                .synopisis("Synopisis")
                .director("Director")
                .cast("Cast")
                .releaseDate("ReleaseDate")
                .genre("Romance")
                .runningTime("1 hour")
                .build();
    }
}
