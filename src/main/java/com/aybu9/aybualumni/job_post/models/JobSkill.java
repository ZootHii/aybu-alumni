package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.user.models.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_skills")
public class JobSkill {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_job_post_id", referencedColumnName = "id", nullable = false)
    private JobPost ownerJobPost;
}
