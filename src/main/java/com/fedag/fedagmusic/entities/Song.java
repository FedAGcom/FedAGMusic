package com.fedag.fedagmusic.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
