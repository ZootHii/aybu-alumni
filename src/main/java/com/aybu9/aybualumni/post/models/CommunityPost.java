package com.aybu9.aybualumni.post.models;

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
@Table(name = "community_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class CommunityPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_community_page_id", referencedColumnName = "id", nullable = false)
    private CommunityPage ownerCommunityPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false) // todo denenecek geliyor mu default everyone
    private String visibility; // -> EVERYONE / COMMUNITY_SECTOR // ya herkes ya da bu sectorde bulunan kişiler kulüp
}
