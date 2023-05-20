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
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;

    @OneToOne
    private User requestFrom;
    @OneToOne
    private User requestFor;

}
