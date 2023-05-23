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
    private String name;
    private String description;
    private Boolean isSuspended;
    private String suspensionReason;
}
