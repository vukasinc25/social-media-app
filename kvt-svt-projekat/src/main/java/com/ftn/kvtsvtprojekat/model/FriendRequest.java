package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class FriendRequest {
    @Id
    private Long id;
    private boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;

}
