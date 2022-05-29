package com.aybu9.aybualumni.event.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.page.models.City;
import com.aybu9.aybualumni.user.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
public class Event extends LongBaseModel {

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    @Column(length = 2048)
    @Size(max = 2048) // max url 2048
    private String fileUrl; // pdf excel resim video falan gibi

    @Size(max = 1000)
    private String description;

    @Column(nullable = false)
    @NotNull
    private Boolean isOnline;

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 2048)
    private String address; // online ise address zoom adresi online değilse normal kılavuz mahallesi

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm") // time zone eklenmesi gerekirse eğer Europe/Istanbul
    @FutureOrPresent // https://www.baeldung.com/javax-validation
    @Column(nullable = false)
    private Date startDateTime;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm") // time zone eklenmesi gerekirse eğer Europe/Istanbul
    @Future // https://www.baeldung.com/javax-validation
    @Column(name = "end_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE CONSTRAINT end_greater_than_start CHECK (end_date_time > start_date_time)", nullable = false)
    private Date endDateTime; // startdate time dan ileri olacak

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> eventSpeakerUsers = new HashSet<>();

    public Event(String name, User ownerUser, Boolean isOnline,
                 String address, Date startDateTime, Date endDateTime) {
        this.name = name;
        this.ownerUser = ownerUser;
        this.isOnline = isOnline;
        this.address = address;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
