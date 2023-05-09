package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Banned {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    private LocalDateTime banTime;

}
