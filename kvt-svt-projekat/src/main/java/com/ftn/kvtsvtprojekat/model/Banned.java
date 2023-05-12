package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
