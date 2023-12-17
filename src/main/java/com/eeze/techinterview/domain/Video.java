package com.eeze.techinterview.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob
    private byte[] data;

    @Column
    private String title;

    @Column
    private String synopisis;

    @Column
    private String director;

    @Column
    private String videoCast;

    @Column
    private String releaseDate;

    @Column
    private String genre;

    @Column
    private String runningTime;

    @Column(columnDefinition = "boolean default false", name = "delist")
    private boolean deList;

}
