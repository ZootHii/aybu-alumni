package com.aybu9.aybualumni.event.models;

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
@Table(name = "company_events", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "event_id"
        })
})
public class CompanyEvent {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Event event;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_company_page_id", referencedColumnName = "id", nullable = false)
    private CompanyPage ownerCompanyPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false)
    private String visibility; // -> EVERYONE / COMPANY_FOLLOWERS / COMPANY_MEMBERS / COMPANY_SECTOR // ya herkes ya da bu sectorde çalışan kişiler ya da sadece company de çalışan kişiler
    // FOLLOWERS = EVERYONE
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
