package com.ftn.kvtsvtprojekat.model;

import com.ftn.kvtsvtprojekat.model.enums.ReactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class Reaction {
    @Id
    private Long id;
    private ReactionType reactionType;
    private LocalDateTime reactionTime;
    @OneToOne
    private User user;

    public Reaction() {}

    public Reaction(Long id, ReactionType reactionType, LocalDateTime reactionTime, User user) {
        this.id = id;
        this.reactionType = reactionType;
        this.reactionTime = reactionTime;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ReactionType getReactionType() {
        return reactionType;
    }
    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
    public LocalDateTime getReactionTime() {
        return reactionTime;
    }
    public void setReactionTime(LocalDateTime reactionTime) {
        this.reactionTime = reactionTime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", reactionType=" + reactionType +
                ", reactionTime=" + reactionTime +
                ", user=" + user +
                '}';
    }
}
