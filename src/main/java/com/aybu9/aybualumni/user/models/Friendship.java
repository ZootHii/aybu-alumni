package com.aybu9.aybualumni.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
public class Friendship {

    // sender or user
    // receiver friend
    // https://stackoverflow.com/questions/2910134/friendship-database-schema

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "BIGINT CONSTRAINT columns_not_equal CHECK (sender_id != receiver_id)", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    @NotNull
    private Boolean isAccepted;
    
    @JsonIgnore
    @Column(name = "greater_id", columnDefinition = "BIGINT GENERATED ALWAYS AS (CASE WHEN sender_id > receiver_id THEN sender_id ELSE receiver_id END) STORED", nullable = false, insertable = false)
    private Long greaterId;

    @JsonIgnore
    @Column(name = "lesser_id", columnDefinition = "BIGINT GENERATED ALWAYS AS (CASE WHEN sender_id <= receiver_id THEN sender_id ELSE receiver_id END) STORED", nullable = false, insertable = false)
    private Long lesserId;

    public Friendship(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    
    public Friendship(User sender, User receiver, Boolean isAccepted) {
        this.sender = sender;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
    }

    public Friendship(Long id, User sender, User receiver, Boolean isAccepted) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Friendship that = (Friendship) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
