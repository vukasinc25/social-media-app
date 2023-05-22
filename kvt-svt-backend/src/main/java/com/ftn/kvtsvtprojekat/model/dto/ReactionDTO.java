package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionDTO {
    private Long id;
    private String reactionType;
    private Boolean isDeleted;
    private Long userId;
    private Long postId;
    private Long commentId;
}
