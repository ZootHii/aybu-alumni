package com.aybu9.aybualumni.post.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @Size(max = 1000)
    private String description;
    
    private String visibility;
}
