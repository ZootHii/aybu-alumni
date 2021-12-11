package com.aybu9.aybualumni.user.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_contact_infos")
public class UserContactInfo {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048)
    @Size(max = 2048)
    private String websiteUrl;

    @Column(length = 2048)
    @Size(max = 2048)
    private String resumeUrl; // cv yi yükleyecek amazona ordaki url i set edecez
    
    @Email
    @Size(max = 255) // min gelebilir 
    private String email; // contact email kayıt emailden ayrı ama default olarak kayıt email i eklenebilir gibi gösterilebielir
    
    @Column(length = 10)
    @Pattern(regexp="[\\d]{7,10}", message = "min 7 max 10 karakterli sayı") // 7 yada 10 sadece olmalı 8 9 olmamalı
    private String phoneNumber;

    // https://www.baeldung.com/spring-date-parameters#convert-date-parameters-on-request-level
    // https://www.baeldung.com/spring-boot-formatting-json-dates#1-setting-the-format
    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent
    private Date birthday;
    
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
    public UserContactInfo(String email) {
        this.email = email;
    }
}
