package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
