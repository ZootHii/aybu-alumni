package com.aybu9.aybualumni.post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.CompanyPage;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class CompanyPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_company_page_id", referencedColumnName = "id", nullable = false)
    private CompanyPage ownerCompanyPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false)
    private String visibility; // -> EVERYONE / COMPANY_SECTOR // ya herkes ya da bu sectorde çalışan kişiler
}
