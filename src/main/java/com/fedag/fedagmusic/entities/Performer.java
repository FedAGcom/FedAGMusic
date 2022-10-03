package com.fedag.fedagmusic.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "performer")
public class Performer {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer subscribersCount;
    @Transient
    private User user;

    public Performer(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Performer(Long id) {
        this.id = id;
    }
}
