package com.aybu9.aybualumni.page.models;

import com.aybu9.aybualumni.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "page_url"
        })
})
public class Page {
    
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
    
    @Lob
    private String about;

    @Column(length = 510)
    @Size(max = 510)
    private String slogan;

    @Column(length = 2048)
    @Size(max = 2048)
    private String websiteUrl;

    @Column(length = 4)
    @Size(min = 4, max = 4)
    private String foundationYear;

    @Column(length = 10)
    @Pattern(regexp="[\\d]{7,10}", message = "min 7 max 10 karakterli sayı") // 7 yada 10 sadece olmalı 8 9 olmamalı
    private String phoneNumber;
    
    @Column(name = "page_url", nullable = false, updatable = false, length = 2048)
    @Size(max = 2048)
    @NotNull
    @NotBlank
    @URL
    private String pageUrl;

    @Column(length = 2048)
    @Size(max = 2048)
//    @URL
    private String logoPhotoUrl;

    @Column(length = 2048)
    @Size(max = 2048)
//    @URL
    private String coverPhotoUrl;
    
//    @NotNull
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Page(String name, User ownerUser, String pageUrl) {
        this.name = name;
        this.ownerUser = ownerUser;
        this.pageUrl = pageUrl;
    }

    public Page(String name, User ownerUser, String pageUrl, String websiteUrl, String slogan) {
        this.name = name;
        this.ownerUser = ownerUser;
        this.pageUrl = pageUrl;
        this.websiteUrl = websiteUrl;
        this.slogan = slogan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Page page = (Page) o;
        return id != null && Objects.equals(id, page.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
