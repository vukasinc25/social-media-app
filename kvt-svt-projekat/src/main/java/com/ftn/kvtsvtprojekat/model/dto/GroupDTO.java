package com.ftn.kvtsvtprojekat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
