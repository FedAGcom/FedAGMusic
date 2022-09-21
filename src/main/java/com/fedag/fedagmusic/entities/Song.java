package com.fedag.fedagmusic.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "song")
public class Song {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime created;
//    @ManyToOne
//    @JoinColumn(name = "performer_id")
//    private Performer performer;
//    @ManyToOne
//    @JoinColumn(name = "album_id")
//    private Album album;
}
