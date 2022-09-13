package com.fedag.fedagmusic.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;



import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Table(name = "users")
@NoArgsConstructor
@Builder
public class User  {
    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            //   @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime created;


    public User(Long id, String email, String password, String firstName, String lastName, UserRole role, LocalDateTime created) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.created = created;
    }

    //    @ManyToMany
//    @JoinColumn
//    private Playlist playlistIds;

}
