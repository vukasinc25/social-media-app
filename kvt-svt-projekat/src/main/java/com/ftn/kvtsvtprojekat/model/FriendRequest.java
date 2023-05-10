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
public class FriendRequest {
    @Id
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
