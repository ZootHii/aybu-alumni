package com.aybu9.aybualumni.event.models;

import com.aybu9.aybualumni.page.models.City;
import com.aybu9.aybualumni.user.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    @Column(length = 2048)
    @Size(max = 2048) // max url 2048
//    @URL
    private String fileUrl; // pdf excel resim video falan gibi

    @Lob // size belirtmiyorsak lob
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    @NotNull
    private Boolean isOnline;

    @NotNull
    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 2048)
    private String address; // online ise address zoom adresi online değilse normal kılavuz mahallesi

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm") // time zone eklenmesi gerekirse eğer Europe/Istanbul
    @FutureOrPresent // https://www.baeldung.com/javax-validation
    @Column(nullable = false)
    private Date startDateTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm") // time zone eklenmesi gerekirse eğer Europe/Istanbul
    @Future // https://www.baeldung.com/javax-validation
    @Column(name = "end_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE CONSTRAINT end_greater_than_start CHECK (end_date_time > start_date_time)", nullable = false)
    private Date endDateTime; // startdate time dan ileri olacak

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_speakers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
