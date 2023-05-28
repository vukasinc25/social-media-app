package com.ftn.kvtsvtprojekat.model.dto;

import com.ftn.kvtsvtprojekat.model.enums.ReportReason;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDTO {
    private Long id;
    private String reportReason;
    private LocalDateTime reportTime;
    private Boolean isAccepted;

    private Long byUserId;
    private Long groupId;
    private Long reportedUserId;
    private Long postId;
    private Long commentId;
}
