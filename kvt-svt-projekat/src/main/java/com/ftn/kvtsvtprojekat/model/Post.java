package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    private Long id;
    private String content;
    @ElementCollection
    private List<String> imagePaths;
    private LocalDateTime creationDate;
    @OneToOne
    private User user;

    public Post() {}

    public Post(Long id, String content, List<String> imagePaths, LocalDateTime creationDate, User user) {
        this.id = id;
        this.content = content;
        this.imagePaths = imagePaths;
        this.creationDate = creationDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getImagePaths() {
        return imagePaths;
    }
    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", imagePaths=" + imagePaths +
                ", creationDate=" + creationDate +
                ", user=" + user +
                '}';
    }
}
