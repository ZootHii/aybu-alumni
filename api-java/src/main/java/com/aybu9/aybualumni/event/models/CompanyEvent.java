package com.aybu9.aybualumni.event.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.CompanyPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class CompanyEvent extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_company_page_id", referencedColumnName = "id", nullable = false)
    private CompanyPage ownerCompanyPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(/*columnDefinition = "VARCHAR(255) DEFAULT 'EVERYONE'",*/ nullable = false)
    @Size(max = 255)
    @NotBlank
    @NotNull
    private String visibility; // -> EVERYONE / COMPANY_FOLLOWERS / COMPANY_MEMBERS / COMPANY_SECTOR // ya herkes ya da bu sectorde çalışan kişiler ya da sadece company de çalışan kişiler
    // FOLLOWERS = EVERYONE
}
