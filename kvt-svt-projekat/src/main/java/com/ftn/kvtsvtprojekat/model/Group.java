package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Group {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private LocalDateTime creationDate;
    private Boolean isSuspended;
    private String suspensionReason;

    @OneToMany
    private Set<Post> post = new HashSet<>();

}