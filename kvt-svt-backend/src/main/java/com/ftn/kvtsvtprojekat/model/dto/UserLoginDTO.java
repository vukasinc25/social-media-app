package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
