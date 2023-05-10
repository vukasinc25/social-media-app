package com.ftn.kvtsvtprojekat.model;

import com.ftn.kvtsvtprojekat.model.enums.ReactionType;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Reaction {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private ReactionType reactionType;
    @NotNull
    private LocalDateTime reactionTime;

    @NotNull
    @OneToOne
    private User user;

    @OneToOne
    private Post post;
    @OneToOne
    private Comment comment;
}
