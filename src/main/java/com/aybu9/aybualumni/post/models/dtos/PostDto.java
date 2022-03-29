package com.aybu9.aybualumni.post.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @Lob
    private String description;

//    @Size(max = 2048)
//    private String fileUrl;
    
    private String visibility;
}
