package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_job_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "job_post_id"
        })
})
public class CompanyJobPost {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private JobPost jobPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_company_page_id", referencedColumnName = "id", nullable = false)
    private CompanyPage ownerCompanyPage;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
