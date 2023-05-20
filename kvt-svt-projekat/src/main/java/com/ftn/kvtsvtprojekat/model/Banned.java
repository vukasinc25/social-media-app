package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Banned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private LocalDateTime banTime;

    @OneToOne
    private User user;
    @OneToOne
    private User admin;
    @OneToOne
    private Group group;
    @OneToOne
    private GroupAdmin groupAdmin;
}
