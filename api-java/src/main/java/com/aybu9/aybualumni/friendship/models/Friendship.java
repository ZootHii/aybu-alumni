package com.aybu9.aybualumni.friendship.models;

import com.aybu9.aybualumni.core.models.LongBaseModel;
import com.aybu9.aybualumni.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(name = "together_unique", columnNames = {
                "sender_id",
                "receiver_id"

        }),
        @UniqueConstraint(name = "together_unique_2", columnNames = {
                "greater_id",
                "lesser_id"
        })
})
public class Friendship extends LongBaseModel {

    // sender or user
    // receiver friend
    // https://stackoverflow.com/questions/2910134/friendship-database-schema

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "BIGINT CONSTRAINT columns_not_equal CHECK (sender_id != receiver_id)", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    @NotNull
    private Boolean isAccepted;
    
    @JsonIgnore
    @Column(name = "greater_id", columnDefinition = "BIGINT GENERATED ALWAYS AS (CASE WHEN sender_id > receiver_id THEN sender_id ELSE receiver_id END) STORED", nullable = false, insertable = false)
    private Long greaterId;

    @JsonIgnore
    @Column(name = "lesser_id", columnDefinition = "BIGINT GENERATED ALWAYS AS (CASE WHEN sender_id <= receiver_id THEN sender_id ELSE receiver_id END) STORED", nullable = false, insertable = false)
    private Long lesserId;
    
    public Friendship(User sender, User receiver, Boolean isAccepted) {
        this.sender = sender;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
    }
}
