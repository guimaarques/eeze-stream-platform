package com.eeze.techinterview.service.impl;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.dto.VideoResponseDto;
import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.exception.VideoAlreadyExistsException;
import com.eeze.techinterview.exception.VideoNotFoundException;
import com.eeze.techinterview.repository.VideoRepository;
import com.eeze.techinterview.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoRepository repo;

    @Override
    public Video getVideo(String name) {
        Video video = repo.findByName(name);
        if(isNull(video) || video.isDeList()){
            throw new VideoNotFoundException("Video Not Found try to search with another name");
        }
        return video;
    }

    @Override
    public List<VideoResponseDto> getAllVideosNames() {
        List<Video> allEntryNames = repo.getAllEntryNames();
        List<VideoResponseDto> response = new ArrayList<>();

        allEntryNames.forEach(item -> {
            VideoResponseDto responseDto = new VideoResponseDto();
            responseDto.setName(item.getName());
            response.add(responseDto);
        });

        return response;
    }

    @Override
    public void saveVideo(MultipartFile file, String name) throws IOException {
        if(repo.existsByName(name))
            throw new VideoAlreadyExistsException("Video " + name + " already exist on Eeze Platform");

        try {
            Video newRelease = Video.builder()
                    .data(file.getBytes())
                    .name(name)
                    .build();
            repo.save(newRelease);
        }catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    @Override
    public VideoResponseDto getVideoMetadatasByName(String name) {
        Video byName = getVideo(name);
        return VideoResponseDto.builder()
                .cast(byName.getVideoCast())
                .genre(byName.getGenre())
                .name(byName.getName())
                .title(byName.getTitle())
                .director(byName.getDirector())
                .releaseDate(byName.getReleaseDate())
                .runningTime(byName.getRunningTime())
                .synopisis(byName.getSynopisis())
                .build();
    }

    @Override
    public VideoResponseDto addMetadata(String name, VideoRequestDto request) throws IOException {

        try {
            Video video = getVideo(name);
            video.setTitle(request.getTitle());
            video.setSynopisis(request.getSynopisis());
            video.setDirector(request.getDirector());
            video.setVideoCast(request.getCast());
            video.setReleaseDate(request.getReleaseDate());
            video.setGenre(request.getGenre());
            video.setRunningTime(request.getRunningTime());

            repo.save(video);
            return VideoResponseDto.of(video);
        } catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    @Override
    public void deListVideo(String name) throws IOException {
        try {
            Video video = getVideo(name);
            video.setDeList(true);
            repo.save(video);
        }
        catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }
}
