package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean isDeleted;
    private Long userId;
    private Long groupId;
    private LocalDateTime creationDate;
}
