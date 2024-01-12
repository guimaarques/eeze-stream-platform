package com.eeze.techinterview.service.impl;

import com.eeze.techinterview.controller.dto.VideoRequestDto;
import com.eeze.techinterview.controller.enums.StatisticMetricEnum;
import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.exception.VideoAlreadyExistsException;
import com.eeze.techinterview.exception.VideoException;
import com.eeze.techinterview.exception.VideoNotFoundException;
import com.eeze.techinterview.repository.VideoRepository;
import com.eeze.techinterview.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private VideoRepository repository;

    @Override
    public Video getVideo(String name) {
        Video video = repository.findByName(name);
        if(isNull(video) || video.isDeList()){
            throw new VideoNotFoundException("Video Not Found try to search with another name");
        }
        return video;
    }

    @Override
    public List<Video> getAllVideosNames() {
        return repository.getAllEntryNames();
    }

    @Override
    public List<Video> getVideosByQuery(Object field, Object value) {
        List<Video> video = repository.getEntriesByQuery(value);

        if(video.isEmpty())
            throw new VideoNotFoundException("Video Not Found for " + field + " equal " + value);

        return video;
    }

    @Override
    public Video saveVideo(MultipartFile file, String name) {
        if(repository.existsByName(name))
            throw new VideoAlreadyExistsException("Video " + name + " already exist on Eeze Platform");

        try {
            Video newRelease = Video.builder()
                    .data(file.getBytes())
                    .name(name)
                    .build();
            return repository.save(newRelease);

        }catch (Exception exception) {
            throw new VideoException(exception.getMessage());
        }
    }


    @Override
    public Video addMetadata(String name, VideoRequestDto request) {

        try {
            Video video = getVideo(name);
            video.setTitle(request.getTitle());
            video.setSynopisis(request.getSynopisis());
            video.setDirector(request.getDirector());
            video.setVideoCast(request.getCast());
            video.setReleaseDate(request.getReleaseDate());
            video.setGenre(request.getGenre());
            video.setRunningTime(request.getRunningTime());

            return repository.save(video);
        } catch (Exception exception) {
            throw new VideoException(exception.getMessage());
        }
    }

    @Override
    public void deListVideo(String name)  {
        try {
            Video video = getVideo(name);
            video.setDeList(true);
            repository.save(video);
        }
        catch (Exception exception) {
            throw new VideoException(exception.getMessage());
        }
    }

    @Override
    public void increaseVideoStatistic(String name, StatisticMetricEnum statisticMetricEnum) {
        Video video = getVideo(name);
        try {
            switch (statisticMetricEnum) {
                case VIEWS -> video.setViews(video.getViews() + 1);
                case IMPRESSIONS -> video.setImpressions(video.getImpressions() + 1);
            }
            repository.save(video);
        } catch (Exception exception) {
            throw new VideoNotFoundException(exception.getMessage());
        }
    }
}
