package com.aybu9.aybualumni.user.models;


import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @URL
    private String websiteUrl;

    @Column(length = 2048)
    @Size(max = 2048)
    @URL
    private String resumeUrl; // cv yi yükleyecek amazona ordaki url i set edecez
    
    @Email
    @Size(max = 255) // min gelebilir 
    private String email; // contact email kayıt emailden ayrı ama default olarak kayıt email i eklenebilir gibi gösterilebielir
    
    @Column(length = 10)
    @Pattern(regexp="[\\d]{7,10}", message = "min 7 max 10 karakterli sayı") // 7 yada 10 sadece olmalı 8 9 olmamalı
    private String phoneNumber;
    
}
