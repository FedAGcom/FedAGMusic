package com.fedag.fedagmusic.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Table(name = "album")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Album {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime created;
    @Transient
    private Performer performer;

    public Album(Long id, String title, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.created = created;
    }
}
