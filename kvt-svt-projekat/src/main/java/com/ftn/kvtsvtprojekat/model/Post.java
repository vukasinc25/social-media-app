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
@RequiredArgsConstructor
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
    @NotNull
    @OneToOne
    private User user;

    public Post(Long id, String content, List<Image> imagePaths, LocalDateTime creationDate, User user) {
        this.id = id;
        this.content = content;
        this.imagePaths = imagePaths;
        this.creationDate = creationDate;
        this.user = user;
    }

}
