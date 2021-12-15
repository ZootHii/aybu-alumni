package com.aybu9.aybualumni.event.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.CommunityPage;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_events", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "event_id"
        })/*,
        @UniqueConstraint(columnNames = {
                "owner_community_page_id"
        })*/
})
public class CommunityEvent extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_community_page_id", referencedColumnName = "id", nullable = false)
    private CommunityPage ownerCommunityPage;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(/*columnDefinition = "VARCHAR(255) DEFAULT 'EVERYONE'",*/ nullable = false)
    @Size(max = 255)
    @NotBlank
    @NotNull
    private String visibility; // -> EVERYONE / COMMUNITY_FOLLOWERS / COMMUNITY_MEMBERS / COMMUNITY_SECTOR // ya herkes ya da bu sectorde bulunan kişiler kulüp
    // FOLLOWERS = EVERYONE
}
