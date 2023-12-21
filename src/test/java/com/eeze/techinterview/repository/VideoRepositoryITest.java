package com.eeze.techinterview.repository;

import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.helper.VideoGenerator;
import com.eeze.techinterview.util.QueryUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VideoRepositoryITest {

    @Autowired
    private VideoRepository repository;

    @Test
    public void findByName() {
        Video videoExpected = VideoGenerator.videoResponseDto();
        repository.save(VideoGenerator.videoResponseDto());

        final Video videoFound = repository.findByName(VideoGenerator.videoResponseDto().getName());

        assertEquals(videoFound.getName(), videoExpected.getName());
    }

    @Test
    public void getAllVideosFromBase(){
        List<Video> videos = VideoGenerator.videosResponseDto();
        repository.save(VideoGenerator.videoResponseDto());

        final List<Video> allEntryNames = repository.getAllEntryNames();

        assertEquals(videos, allEntryNames);
    }

    @Test
    public void saveAVideo(){
        repository.save(VideoGenerator.videoResponseDto());
        assert(repository.existsByName("Test"));
    }

    @Test
    public void findVideoByQuery() {

        Object value = "Director";
        QueryUtil.setField("director");
        repository.save(VideoGenerator.videoWithMetadata());

        final List<Video> videosByQuery = repository.getEntriesByQuery(value);

        assertThat(videosByQuery).anyMatch(e -> e.getDirector().equals(
                VideoGenerator.videoWithMetadata().getDirector()));
    }


}
