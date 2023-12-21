package com.eeze.techinterview.service;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.enums.StatisticMetricEnum;
import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.exception.VideoAlreadyExistsException;
import com.eeze.techinterview.exception.VideoNotFoundException;
import com.eeze.techinterview.helper.VideoGenerator;
import com.eeze.techinterview.helper.VideoRequestDtoGenerator;
import com.eeze.techinterview.repository.VideoRepository;
import com.eeze.techinterview.service.impl.VideoServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VideoServiceUTest {


    private static VideoService service;

    private static VideoRepository repository;

    @BeforeAll
    public static void setup(){
        repository = mock(VideoRepository.class);
        service = new VideoServiceImpl(repository);
    }

    @BeforeEach
    public void setUpBeforeEach() {
        reset(repository);
        clearInvocations(repository);
    }

    @Test
    public void throwNotFoundWhenTryToGetANotExistentVideo() {
        Video expectedVideo = VideoGenerator.videoWithMetadata();
        when(repository.findByName(expectedVideo.getName())).thenThrow(VideoNotFoundException.class);

        assertThrows(VideoNotFoundException.class, () -> service.getVideo(expectedVideo.getName()));
    }

    @Test
    public void getVideo() {
        Video expectedVideo = VideoGenerator.videoWithMetadata();
        when(repository.findByName(expectedVideo.getName())).thenReturn(expectedVideo);

        Video actualVideo = service.getVideo(expectedVideo.getName());

        assertEquals(actualVideo, expectedVideo);
    }

    @Test
    public void throwNotFoundWhenVideoIsDelisted(){
        Video expectedVideo = VideoGenerator.videoWithMetadataDeListed();
        when(repository.findByName(expectedVideo.getName())).thenReturn(expectedVideo);

        assertThrows(VideoNotFoundException.class, () -> service.getVideo(expectedVideo.getName()));
    }

    @Test
    public void getAllVideosNames() {
        final List<Video> expectedVideo = VideoGenerator.videosResponseDto();
        when(repository.getAllEntryNames()).thenReturn(expectedVideo);

        final List<Video> actualVideo = service.getAllVideosNames();

        assertEquals(actualVideo, expectedVideo);
    }

    @Test
    public void getAllVideosByGenericQuery() {
        Object value = "value";
        Object field = "field";

        final List<Video> expectedVideo = VideoGenerator.videosResponseDto();
        when(repository.getEntriesByQuery(value)).thenReturn(expectedVideo);

        final List<Video> actualVideo = service.getVideosByQuery(field, value);

        assertEquals(actualVideo, expectedVideo);

    }

    @Test
    public void throwErrorWhenVideoAlreadyExist() {
        Video expectedVideo = VideoGenerator.videoWithMetadata();
        MultipartFile file = mock(MultipartFile.class);

        when(repository.existsByName(expectedVideo.getName())).thenReturn(true);
        assertThrows(VideoAlreadyExistsException.class, () -> service.saveVideo(file, expectedVideo.getName()));
    }

    @Test
    public void successToSave() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Video expectedVideo = VideoGenerator.videoWithMetadata();

        when(repository.existsByName(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(expectedVideo);

        final Video actualVideo = service.saveVideo(file, any());

        assertEquals(expectedVideo, actualVideo);

    }

    @Test
    public void delistAVideo() {
        final Video expectedVideo = VideoGenerator.videoWithMetadata();

        when(repository.findByName(expectedVideo.getName())).thenReturn(expectedVideo);
        service.deListVideo(expectedVideo.getName());

        verify(repository, times(1)).save(any());

    }

    @Test
    public void addMetadataToAVideo() {
        final VideoRequestDto videoRequestDto = VideoRequestDtoGenerator.videoRequestDto();
        final Video videoWithMetadata = VideoGenerator.videoWithMetadata();

        when(repository.findByName(videoWithMetadata.getName())).thenReturn(videoWithMetadata);
        service.addMetadata(videoWithMetadata.getName(), videoRequestDto);

        verify(repository, times(1)).save(any());

    }

    @Test
    public void validateIncreaseStatisticViewForAVideoByName() throws IOException {
        final Video videoWithMetadata = VideoGenerator.videoWithMetadata();

        when(repository.findByName(videoWithMetadata.getName())).thenReturn(videoWithMetadata);
        service.increaseVideoStatistic(videoWithMetadata.getName(), StatisticMetricEnum.VIEWS);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void validateIncreaseStatisticImpressionsForAVideoByName() throws IOException {
        final Video videoWithMetadata = VideoGenerator.videoWithMetadata();

        when(repository.findByName(videoWithMetadata.getName())).thenReturn(videoWithMetadata);
        service.increaseVideoStatistic(videoWithMetadata.getName(), StatisticMetricEnum.IMPRESSIONS);

        verify(repository, times(1)).save(any());
    }
}
