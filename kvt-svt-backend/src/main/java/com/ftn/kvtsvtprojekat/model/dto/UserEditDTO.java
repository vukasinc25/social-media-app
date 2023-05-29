package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
