package com.aybu9.aybualumni.user.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.models.UserPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
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
public class User extends LongBaseModel {

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

    @Size(max = 255)
    private String headline;

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

    @Size(max = 1000)
    private String about;

    @Column(length = 2048, nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 2048)
//    @URL
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
    @ToString.Exclude
    Set<Friendship> friendships;
}

