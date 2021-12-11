package com.aybu9.aybualumni.event.models;

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
@Table(name = "user_events", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "event_id"
        })
})
public class UserEvent {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Event event;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    // visible_to varchar // bağlantılar görebilir, herkes görebilir,
    @Column(columnDefinition = "VARCHAR DEFAULT 'EVERYONE'", nullable = false)
    private String visibility; // -> FRIENDSHIP / EVERYONE
    
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
