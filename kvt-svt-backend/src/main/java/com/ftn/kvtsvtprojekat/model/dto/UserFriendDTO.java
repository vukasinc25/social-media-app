package com.ftn.kvtsvtprojekat.model.dto;

import com.ftn.kvtsvtprojekat.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendDTO {
    private Long id;
    private Boolean isDeleted;
    private Long userId;
    private Long friendId;
}
