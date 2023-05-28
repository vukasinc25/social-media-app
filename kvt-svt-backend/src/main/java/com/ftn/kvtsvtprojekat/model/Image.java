package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String path;
//    private Boolean isDeleted;

    @OneToOne
    private Post post;

    //Users Profile Picture
    @OneToOne
    private User user;
}
