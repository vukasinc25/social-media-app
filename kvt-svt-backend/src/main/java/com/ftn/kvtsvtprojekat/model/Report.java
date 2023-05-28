package com.ftn.kvtsvtprojekat.model;

import com.ftn.kvtsvtprojekat.model.enums.ReportReason;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private ReportReason reportReason;
    private LocalDateTime reportTime;
    private Boolean isAccepted;
//    private Boolean isDeleted;

    @OneToOne
    private User byUser;
    @OneToOne
    private Group group;
    @OneToOne
    private User reportedUser;
    @OneToOne
    private Post post;
    @OneToOne
    private Comment comment;
}
