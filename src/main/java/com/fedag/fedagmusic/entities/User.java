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
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private UserRole role;

    @Column
    private LocalDateTime created;

    @Transient
    private List<Performer> performer;

    public User(Long id, String email, String password, String firstName, String lastName, LocalDateTime created) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created = created;
    }

    public User(Long id, String email, String password, String firstName, String lastName, UserRole role, LocalDateTime created) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.created = created;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }
    //    @ManyToMany
//    @JoinColumn
//    private Playlist playlistIds;

}
