package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterDTO {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}
