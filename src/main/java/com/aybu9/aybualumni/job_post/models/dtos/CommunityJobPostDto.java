package com.aybu9.aybualumni.job_post.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityJobPostDto {

    @Size(max = 1000)
    private String description;

    @NotNull
    private Integer jobTitleId;

    @NotBlank
    @NotNull
    private String jobType; // FULL TIME, PART TIME, INTERNSHIP, DAILY

    @NotBlank
    @NotNull
    private String workplaceType; // OFFICE, HYBRID, REMOTE

    // online ise önde şehir göstermeyelim değişecek
    private Integer cityId;

    private Collection<Integer> jobSkillsIds;
}
