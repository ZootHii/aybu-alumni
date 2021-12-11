package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.post.models.Post;
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
@Table(name = "community_job_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "job_post_id"
        })
})
public class CommunityJobPost {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private JobPost jobPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_community_page_id", referencedColumnName = "id", nullable = false)
    private CommunityPage ownerCommunityPage;
    
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
