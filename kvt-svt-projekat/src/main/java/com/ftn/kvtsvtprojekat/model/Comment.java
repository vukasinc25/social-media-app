package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String text;
    private Boolean isSuspended;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "comments_replies",
            joinColumns = @JoinColumn(name = "comment_parent"),
            inverseJoinColumns = @JoinColumn(name = "comment_child"))
    private Set<Comment> comments = new HashSet<>();

    @OneToOne
    private Post post;
}
