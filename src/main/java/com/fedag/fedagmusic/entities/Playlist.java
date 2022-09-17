package com.fedag.fedagmusic.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "playlist")
@Builder
public class Playlist {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime created;
    @Transient
    private User user;
//    @Transient
//    private List<Song> songs;
}
