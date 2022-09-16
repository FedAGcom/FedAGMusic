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
@Table(name = "playlist")
public class Playlist {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime created;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    @OneToMany
//    private Song songs;
}
