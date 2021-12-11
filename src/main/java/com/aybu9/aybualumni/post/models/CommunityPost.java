package com.aybu9.aybualumni.post.models;

import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
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
@Table(name = "community_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class CommunityPost {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_community_page_id", referencedColumnName = "id", nullable = false)
    private CommunityPage ownerCommunityPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false) // todo denenecek geliyor mu default everyone
    private String visibility; // -> EVERYONE / COMMUNITY_SECTOR // ya herkes ya da bu sectorde bulunan kişiler kulüp

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
