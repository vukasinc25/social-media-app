package com.ftn.kvtsvtprojekat.model;

import com.ftn.kvtsvtprojekat.model.enums.Roles;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastLogin;
    private String firstName;
    private String lastName;
    private String profileImagePath;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private Boolean isDeleted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<GroupAdmin> groupAdmins = new HashSet<>();

}
