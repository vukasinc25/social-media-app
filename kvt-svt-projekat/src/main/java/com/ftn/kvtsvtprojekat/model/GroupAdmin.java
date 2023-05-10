package com.ftn.kvtsvtprojekat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GroupAdmin {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    private User user;
    @OneToOne
    private Group group;
}
