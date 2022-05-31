package com.aybu9.aybualumni.user.repositories;

import com.aybu9.aybualumni.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT User FROM User where User.email = ?1")
    Optional<User> getByEmail(String email);

    Optional<User> getByProfileUrl(String profileUrl);

    Boolean existsByEmail(String email);

    Boolean existsByProfileUrl(String profileUrl);

    Set<User> getAllByIdIn(Set<Long> ids);

//    @Query(nativeQuery = true)
//    Collection<User> getFriends(Long userId);

    @Query("select u from User u where (lower(u.name) like lower(concat('%', concat(:name, '%'))) or " +
            "lower(u.name) like lower(concat('', concat(:name, '%'))))")
    Page<User> searchByNameContainsOrStartsWith(@Param("name") String name, Pageable pageable);
}
