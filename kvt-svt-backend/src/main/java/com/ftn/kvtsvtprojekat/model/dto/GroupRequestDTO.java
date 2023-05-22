package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupRequestDTO {
    private Long id;
    private Boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    private Boolean isDeleted;
    private Long groupId;
    private Long userId;
}
