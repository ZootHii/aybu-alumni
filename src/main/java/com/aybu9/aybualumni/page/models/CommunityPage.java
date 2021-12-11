package com.aybu9.aybualumni.page.models;

import com.aybu9.aybualumni.post.models.CommunityPost;
import com.aybu9.aybualumni.sector.models.CommunitySector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_pages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "page_id"
        })
})
public class CommunityPage {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private Page page;

    @OneToOne
    @JoinColumn(name = "community_sector_id", referencedColumnName = "id")
    private CommunitySector communitySector;

    @JsonIgnore
    // set owned pages;
    @OneToMany(mappedBy = "ownerCommunityPage")
    @ToString.Exclude
    private Set<CommunityPost> ownedCommunityPosts = new HashSet<>();
    
    public CommunityPage(Page page, CommunitySector communitySector) {
        this.page = page;
        this.communitySector = communitySector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommunityPage that = (CommunityPage) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
