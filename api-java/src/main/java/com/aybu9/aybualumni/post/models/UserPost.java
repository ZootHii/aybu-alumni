package com.aybu9.aybualumni.post.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id"
        })
})
public class UserPost extends LongBaseModel {

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Post post;
    
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
//    private User ownerUser;

    @Column(nullable = false)
    @Size(max = 255)
    @NotBlank
    @NotNull
    private String visibility; // -> FRIENDSHIP / EVERYONE
}
