package com.eeze.techinterview.controller;

import com.eeze.techinterview.controller.dto.VideoResponseDataDto;
import com.eeze.techinterview.controller.dto.VideoStatisticsResponseDto;
import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.helper.VideoGenerator;
import com.eeze.techinterview.mock.ITest;
import com.eeze.techinterview.service.VideoService;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ITest
public class VideoControllerITest {

    @LocalServerPort
    private int port;

    @MockBean
    private VideoService service;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void postNewVideo() throws IOException {

        Video video = VideoGenerator.videoResponseDto();

        when(service.saveVideo(any(),any())).thenReturn(video);

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","src/main/resources/video/ETAPA3-REBARBAMENTOEPRIMER.mp4",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("src/main/resources/video/ETAPA3-REBARBAMENTOEPRIMER.mp4")))
                .addFormDataPart("name", "test.mp4")
                .build();

        Request request = new Request.Builder()
                .url(uri)
                .method("POST", requestBody)
                .build();
        Response response = client.newCall(request).execute();

        assertEquals(response.code(), 200);
        verify(service, times(1)).saveVideo(any(), any());

    }

    @Test
    public void putMetadataInAVideo()  {

        String videoName = "test.mp4";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/" + videoName;

        Video video = VideoGenerator.videoResponseDto();

        when(service.addMetadata(any(), any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String body = """
                {
                    "title":"How to lose a guy in 10 days",
                    "synopisis":"romance",
                    "director":"Isabella",
                    "cast":"warner",
                    "release_date":"yesterday",
                    "genre":"romcom",
                    "running_time":"one hour"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<VideoResponseDataDto> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, VideoResponseDataDto.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
        verify(service, times(1)).addMetadata(any(), any());

    }

    @Test
    public void getContentToPlay() {

        String videoName = "test.mp4";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/" + videoName + "/play";

        Video video = VideoGenerator.videoResponseDto();

        when(service.getVideo(any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Resource> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Resource.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
        verify(service, times(1)).getVideo(any());

    }

    @Test
    public void getVideoByName() {

        String videoName = "test.mp4";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/" + videoName;

        Video video = VideoGenerator.videoResponseDto();

        when(service.getVideo(any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<VideoResponseDataDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, VideoResponseDataDto.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
        verify(service, times(1)).getVideo(any());

    }

    @Test
    public void getVideos() {

        List<Video> videos = VideoGenerator.videosResponseDto();

        when(service.getAllVideosNames()).thenReturn(videos);

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, VideoResponseDataDto.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
        verify(service, times(1)).getAllVideosNames();
    }

    @Test
    public void deleteVideoByName()  {

        String videoName = "test.mp4";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/" + videoName;

        Video video = VideoGenerator.videoResponseDto();

        when(service.getVideo(any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
        verify(service, times(1)).deListVideo(any());

    }

    @Test
    public void retrieveStatistic() {

        String videoName = "test.mp4";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/" + videoName + "/statistics";

        Video video = VideoGenerator.videoResponseDto();

        when(service.getVideo(any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<VideoStatisticsResponseDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity,
                VideoStatisticsResponseDto.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);

    }

    @Test
    public void retrieveVideMetadataFromQuery(){

        var field = "field";
        var value = "value";

        String url = "http://localhost:" + port + "/eeze-platform";
        String uri = url + "/videos/query?field=" + field + "&value=" + value;

        var video = VideoGenerator.videosResponseDto();

        when(service.getVideosByQuery(any(), any())).thenReturn(video);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);

        assertEquals(responseEntity.getStatusCode().value(), 200);
    }

}
