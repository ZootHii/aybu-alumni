package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.CommunityPage;
import lombok.*;

import javax.persistence.*;

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
public class CommunityJobPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "job_post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private JobPost jobPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_community_page_id", referencedColumnName = "id", nullable = false)
    private CommunityPage ownerCommunityPage;
}
