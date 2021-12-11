package com.aybu9.aybualumni.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContactInfoDto {

    @Size(min = 2, max = 255)
    private String websiteUrl;

    // todo kendi resume url ini kendisi ekleyebilir google docs linki gibi açılabilir
//    @Size(min = 2, max = 255)
//    private String resumeUrl;
    
    @Email
    @Size(min = 5, max = 255)
    private String email;

    @Pattern(regexp="[\\d]{7,10}", message = "min 7 max 10 karakterli sayı")
    private String phoneNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent // https://www.baeldung.com/javax-validation
    private Date birthday;
}

