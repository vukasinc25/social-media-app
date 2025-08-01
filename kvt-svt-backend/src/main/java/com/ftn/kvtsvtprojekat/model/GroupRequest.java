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
public class GroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    private Boolean isBanned;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
