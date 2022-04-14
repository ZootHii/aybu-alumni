package com.aybu9.aybualumni.job_post.models.job;

import com.aybu9.aybualumni.core.models.IntegerBaseModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_titles")
public class JobTitle extends IntegerBaseModel {

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;
}
