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
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String content;
    @OneToMany
    private List<Image> imagePaths;
    @NotNull
    private LocalDateTime creationDate;
    private Boolean isSuspended;

    @NotNull
    @OneToOne
    private User user;
}
