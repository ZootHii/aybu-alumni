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
public class CommunityPageDto {
    
    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 2, max = 255)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-z]+$", message = "URL yalnızca küçük harfler içerebilir")
    private String pageUrl;

    @Size(min = 2, max = 255)
    private String websiteUrl;

    @NotNull
    private Integer communitySectorId;

    @Size(max = 255)
    private String slogan;
}
