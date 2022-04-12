package com.aybu9.aybualumni.user.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileDto {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String surname;

    @Size(max = 255)
    private String headline;
}
