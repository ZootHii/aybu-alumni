package com.aybu9.aybualumni.user.models;

import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.loader.custom.ConstructorResultColumnProcessor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "profileUrl"
        }),
        @UniqueConstraint(columnNames = {
                "user_contact_info_id"
        })
})
@NamedNativeQuery(name = "User.getFriends",
        query = "SELECT * FROM users \n" +
                "WHERE users.id IN (SELECT (CASE WHEN user_id=:userId THEN friend_id ELSE user_id END) AS friend_id FROM friendships WHERE (user_id=:userId OR friend_id=:userId) AND is_accepted = true)\n" +
                "ORDER BY users.id ASC;",
        resultSetMapping = "Mapping.User")


@SqlResultSetMapping(name = "Mapping.User", classes = {
        @ConstructorResult(targetClass = User.class, columns = {
                @ColumnResult(name = "id", type = String.class),
                @ColumnResult(name = "email", type = String.class),
                @ColumnResult(name = "password", type = String.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "surname", type = String.class),
                @ColumnResult(name = "about", type = String.class),
                @ColumnResult(name = "profile_url", type = String.class),
                @ColumnResult(name = "user_contact_info_id", type = String.class),
                @ColumnResult(name = "profile_photo_url", type = String.class),
                @ColumnResult(name = "cover_photo_url", type = String.class),
                @ColumnResult(name = "company_page_id", type = String.class),
                @ColumnResult(name = "community_page_id", type = String.class),
                @ColumnResult(name = "created_at", type = String.class),
                @ColumnResult(name = "updated_at", type = String.class),
        })
})

// todo roller authorities eklenecek
public class User {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false) // column default length 255 
    @NotBlank
    @NotNull
    @Email
    @Size(max = 255)
    // todo @Pattern() ybu.edu ile bitmeli
    private String email;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    @JsonIgnore
    // todo @Pattern() şifre olmalı
    private String password;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String surname;

    @Lob // size belirtmiyorsak lob
    private String about;

    @Column(length = 2048, nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 2048)
    @URL
    private String profileUrl;

    @OneToOne
    @JoinColumn(name = "user_contact_info_id", referencedColumnName = "id", updatable = false)
    private UserContactInfo userContactInfo;

    @Column(length = 2048)
    @Size(max = 2048) // max url 2048
    @URL
    private String profilePhotoUrl;

    @Column(length = 2048)
    @Size(max = 2048)
    @URL
    private String coverPhotoUrl;

    @JsonIgnore
    // set owned pages;
    @OneToMany(mappedBy = "ownerUser")
    @ToString.Exclude
    private Set<Page> ownedPages = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "company_page_id", referencedColumnName = "id")
    private CompanyPage companyPage;

    @OneToOne
    @JoinColumn(name = "community_page_id", referencedColumnName = "id")
    private CommunityPage communityPage;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    // https://stackoverflow.com/questions/62585553/java-spring-boot-jpa-stackoverflowerror-with-a-manytomany-relation
    // todo https://www.baeldung.com/jpa-many-to-many#2-creating-a-composite-key-in-jpa DO THIS FUCKING AAH

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    Set<Friendship> friendships;


//
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "friendships",
//            joinColumns = @JoinColumn(name = "fk_id_user", referencedColumnName = "id", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "fk_id_friend", referencedColumnName = "id", nullable = false)
//    )
//    private Set<User> friends = new HashSet<>();

    public User(String id, String email, String password, String name, String surname, String about, String profileUrl, String userContactInfo, String profilePhotoUrl, String coverPhotoUrl, String companyPage, String communityPage, String createdAt, String updatedAt) {
        this.id = Long.valueOf(id);
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.about = about;
        this.profileUrl = profileUrl;
        if (userContactInfo != null){
            this.userContactInfo.setId(Long.valueOf(userContactInfo));
        }
        this.profilePhotoUrl = profilePhotoUrl;
        this.coverPhotoUrl = coverPhotoUrl;
        if (companyPage != null){

            this.companyPage.setId(Long.valueOf(companyPage));
        }
        if (communityPage != null){
            this.communityPage.setId(Long.valueOf(communityPage));
        }
        if (createdAt != null){
            this.createdAt = java.sql.Date.valueOf(createdAt);
        }
        if (updatedAt != null){
            this.updatedAt = java.sql.Date.valueOf(updatedAt);
        }
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String password, String profileUrl) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

