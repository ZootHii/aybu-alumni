package com.aybu9.aybualumni.post.models;

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
@Table(name = "company_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class CompanyPost {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_company_page_id", referencedColumnName = "id", nullable = false)
    private CompanyPage ownerCompanyPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false)
    private String visibility; // -> EVERYONE / COMPANY_SECTOR // ya herkes ya da bu sectorde çalışan kişiler

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
