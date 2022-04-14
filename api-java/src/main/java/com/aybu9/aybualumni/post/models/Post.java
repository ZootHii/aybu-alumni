package com.aybu9.aybualumni.post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.user.models.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends LongBaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User ownerUser;

    @Column(length = 2048)
    @Size(max = 2048) // max url 2048
//    @URL
    private String fileUrl; // pdf excel resim video falan gibi

    @Size(max = 1000)
    private String description;

    public Post(User ownerUser) {
        this.ownerUser = ownerUser;
    }
}
