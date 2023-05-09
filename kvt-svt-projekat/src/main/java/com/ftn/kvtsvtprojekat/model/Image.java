package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Image {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String path;

    @OneToOne
    private Post post;
    @OneToOne
    private User user;
}