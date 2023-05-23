package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String content;
    private Boolean isDeleted;
    private Long userId;
    private Long groupId;
}
