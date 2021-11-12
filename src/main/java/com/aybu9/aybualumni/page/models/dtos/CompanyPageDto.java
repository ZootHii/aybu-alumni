package com.aybu9.aybualumni.page.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPageDto {
    
    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 2, max = 255)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-z]+$", message = "URL yalnızca küçük harfler içerebilir")
    private String pageUrl; // todo alumni.com/company/{pageUrl}

    @Size(min = 2, max = 255)
    //@URL // todo URL kontrolü burada değil database içerisinde okey ama burada sadece - kullanmaya izin ver regexp
    @Pattern(regexp = "^[a-z]+$", message = "URL yalnızca küçük harfler içerebilir")
    private String websiteUrl;

    @NotNull
    private Integer companySectorId;

    @NotNull
    private Integer cityId;

    @Size(max = 255)
    private String slogan;
}
