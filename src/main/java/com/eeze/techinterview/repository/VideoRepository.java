package com.eeze.techinterview.repository;

import com.eeze.techinterview.domain.Video;
import com.eeze.techinterview.repository.rewriter.MyQueryRewriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findByName(String name);

    boolean existsByName(String name);

    @Query(nativeQuery = true, value="SELECT * FROM video WHERE delist = false")
    List<Video> getAllEntryNames();

    @Query(nativeQuery = true, value = "SELECT * FROM video WHERE field = :value AND delist = false",
            queryRewriter = MyQueryRewriter.class)
    List<Video> getEntriesByQuery(Object value);
}
