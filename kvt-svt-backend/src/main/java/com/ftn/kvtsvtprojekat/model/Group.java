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
@AllArgsConstructor
@Table(name = "groupp")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private LocalDateTime creationDate;
    private Boolean isSuspended;
    private String suspensionReason;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();
//    @OneToOne
//    private User user;
}
