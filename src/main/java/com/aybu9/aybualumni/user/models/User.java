package com.aybu9.aybualumni.user.models;

import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.models.UserPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String nameInCollege;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String surnameInCollege;

    @Lob // size belirtmiyorsak lob
    private String about;

    @Column(length = 2048, nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 2048)
    @URL
    private String profileUrl;
    
    @Column(nullable = false)
    @NotBlank
    @NotNull
    //@Size(min = 1, max = 1)
    private String grade; // 3. ve 4. ve ALUMNI

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String department;

    @OneToOne
    @JoinColumn(name = "user_contact_info_id", referencedColumnName = "id", updatable = false)
    private UserContactInfo userContactInfo;

    @Column(length = 2048)
    @Size(max = 2048) // max url 2048
//    @URL
    private String profilePhotoUrl;

    @Column(length = 2048)
    @Size(max = 2048)
//    @URL
    private String coverPhotoUrl;

    @JsonIgnore
    // set owned pages;
    @OneToMany(mappedBy = "ownerUser")
    @ToString.Exclude
    private Set<Page> ownedPages = new HashSet<>();

    @JsonIgnore
    // set owned pages;
    @OneToMany(mappedBy = "ownerUser")
    @ToString.Exclude
    private Set<Post> ownedPosts = new HashSet<>();

    @JsonIgnore
    // set owned pages;
    @OneToMany(mappedBy = "ownerUser")
    @ToString.Exclude
    private Set<UserPost> ownedUserPosts = new HashSet<>();
    
    @OneToOne
    @JoinColumn(name = "company_page_id", referencedColumnName = "id")
    private CompanyPage companyPage;

    @OneToOne
    @JoinColumn(name = "community_page_id", referencedColumnName = "id")
    private CommunityPage communityPage;

    // https://stackoverflow.com/questions/62585553/java-spring-boot-jpa-stackoverflowerror-with-a-manytomany-relation
    // todo https://www.baeldung.com/jpa-many-to-many#2-creating-a-composite-key-in-jpa DO THIS FUCKING AAH

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    Set<Friendship> friendships;
    
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    

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

    public User(String name, String surname, String email, String password, String profileUrl, String nameInCollege, 
                String surnameInCollege, String grade, String department) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
        this.nameInCollege = nameInCollege;
        this.surnameInCollege = surnameInCollege;
        this.grade = grade;
        this.department = department;
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

