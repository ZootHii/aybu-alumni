package com.aybu9.aybualumni.post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.user.models.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class UserPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false)
    private String visibility; // -> FRIENDSHIP / EVERYONE
}
