package com.eeze.techinterview.helper;

import com.eeze.techinterview.domain.Video;

import java.util.Collections;
import java.util.List;

public final class VideoGenerator {

    public static List<Video> videosResponseDto() {

        Video video = Video.builder()
                .id(1L)
                .name("Test").data("video".getBytes()).build();

        return Collections.singletonList(video);

    }

    public static Video videoResponseDto() {

        return Video.builder()
                .id(1L)
                .name("Test")
                .data("video".getBytes()).build();

    }

    public static Video videoWithMetadata() {
        return Video.builder()
                .id(1L)
                .name("Name")
                .data("video".getBytes())
                .title("Title")
                .synopisis("Synopisis")
                .director("Director")
                .videoCast("Cast")
                .releaseDate("ReleaseDate")
                .genre("Romance")
                .runningTime("1 hour")
                .deList(false)
                .views(0)
                .build();
    }

    public static Video videoWithMetadataDeListed() {
        return Video.builder()
                .id(1L)
                .name("Name")
                .data("video".getBytes())
                .title("Title")
                .synopisis("Synopisis")
                .director("Director")
                .videoCast("Cast")
                .releaseDate("ReleaseDate")
                .genre("Romance")
                .runningTime("1 hour")
                .deList(true)
                .views(0)
                .build();
    }
}
