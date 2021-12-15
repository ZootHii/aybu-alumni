package com.aybu9.aybualumni.job_post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.user.models.User;
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
@Table(name = "user_job_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "job_post_id"
        })
})
public class UserJobPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "job_post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private JobPost jobPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;
}
