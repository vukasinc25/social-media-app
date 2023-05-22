package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private Boolean isDeleted;
    private Long postId;
    private Long userId;
}
