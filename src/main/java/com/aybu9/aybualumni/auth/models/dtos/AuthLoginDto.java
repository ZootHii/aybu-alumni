package com.aybu9.aybualumni.auth.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDto {
    
//    @NotBlank
//    @NotNull
//    @Size(min = 11, max = 11)
//    private String tcIdentityNumber;

    @NotBlank
    @NotNull
    @Email
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @NotNull
    private String password;

}
