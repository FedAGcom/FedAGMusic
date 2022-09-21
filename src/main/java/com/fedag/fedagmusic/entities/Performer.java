package com.fedag.fedagmusic.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

}
