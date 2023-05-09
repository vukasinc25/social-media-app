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
public class User {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private LocalDateTime lastLogin;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String profileImagePath;
    @ManyToMany(mappedBy = "user")
    private Set<User> friends = new HashSet<>();
    @ManyToMany(mappedBy = "friends")
    private Set<User> befriended = new HashSet<>();
}
