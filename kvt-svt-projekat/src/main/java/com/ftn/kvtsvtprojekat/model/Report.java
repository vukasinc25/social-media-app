package com.ftn.kvtsvtprojekat.model;

import com.ftn.kvtsvtprojekat.model.enums.ReportReason;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Report {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull
    private ReportReason reportReason;
    private LocalDateTime reportTime;
    private Boolean isAccepted;

    @OneToOne
    private User byUser;
    @OneToOne
    private Post post;
    @OneToOne
    private Comment comment;
}
