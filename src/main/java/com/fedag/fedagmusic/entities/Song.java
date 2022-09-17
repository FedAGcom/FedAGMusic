package com.fedag.fedagmusic.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "song")
@Builder
public class Song {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime created;
    @Transient
    private Performer performer;
    @Transient
    private Album album;
}
