package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.page.models.City;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.sector.models.CommunitySector;
import com.aybu9.aybualumni.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    @OneToOne
    @JoinColumn(name = "job_title_id", referencedColumnName = "id")
    private JobTitle jobTitle;

    @Column(columnDefinition = "VARCHAR DEFAULT 'OFFICE'", nullable = false) // todo denenecek geliyor mu default everyone
    private String workplaceType; // OFFICE, HYBRID, REMOTE

    @Column(columnDefinition = "VARCHAR DEFAULT 'FULL TIME'", nullable = false) // todo denenecek geliyor mu default everyone
    private String jobType; // FULL TIME, PART TIME, INTERNSHIP, DAILY 

    @JsonIgnore
    @OneToMany(mappedBy = "ownerJobPost")
    @ToString.Exclude
    private Set<JobSkill> jobSkills = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
    
    @Column(length = 2048)
    @Size(max = 2048)
    private String fileUrl; // pdf excel resim video falan gibi

    @Lob
    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
