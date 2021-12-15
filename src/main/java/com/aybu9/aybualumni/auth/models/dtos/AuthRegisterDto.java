package com.aybu9.aybualumni.auth.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterDto {

//    @NotBlank
//    @NotNull
//    @Size(min = 11, max = 11)
//    private String tcIdentityNumber;
    
    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String surname;

    @Size(min = 2, max = 255)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-z]+$", message = "URL yalnızca küçük harfler içerebilir")
    private String profileUrl; // todo alumni.com/users/profile/{profileUrl}

    @NotBlank
    @NotNull
    @Email
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}$",
            message = "At least one UPPER CASE Character " +
                    "At least one lower case character " +
                    "At least one digit " +
                    "At least one special character '@$!%*#?&^_-' " +
                    "Minimum 8 characters.")
    private String password;
}
