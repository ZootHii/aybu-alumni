package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.job_post.models.job.JobSkill;
import com.aybu9.aybualumni.job_post.models.job.JobTitle;
import com.aybu9.aybualumni.page.models.City;
import com.aybu9.aybualumni.user.models.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_posts")
public class JobPost extends LongBaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    @OneToOne
    @JoinColumn(name = "job_title_id", referencedColumnName = "id")
    private JobTitle jobTitle;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String workplaceType; // OFFICE, HYBRID, REMOTE

    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String jobType; // FULL TIME, PART TIME, INTERNSHIP, DAILY 

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "job_posts_job_skills",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "job_skill_id"))
    @ToString.Exclude
    private Set<JobSkill> jobSkills = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(length = 2048)
    @Size(max = 2048)
    private String fileUrl; // pdf excel resim video falan gibi

    @Size(max = 1000)
    private String description;

    public JobPost(User ownerUser, String workplaceType, String jobType) {
        this.ownerUser = ownerUser;
        this.workplaceType = workplaceType;
        this.jobType = jobType;
    }
}
