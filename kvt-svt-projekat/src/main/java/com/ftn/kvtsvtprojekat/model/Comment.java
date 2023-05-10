package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Comment {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String text;
    private Boolean isSuspended;



}
