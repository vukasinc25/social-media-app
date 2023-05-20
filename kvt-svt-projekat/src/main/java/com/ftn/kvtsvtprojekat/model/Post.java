package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String content;
    @OneToMany
    private List<Image> imagePaths;
    private LocalDateTime creationDate;
    private Boolean isSuspended;

    @OneToOne
    private User user;
}
