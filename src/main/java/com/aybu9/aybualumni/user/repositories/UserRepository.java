package com.aybu9.aybualumni.user.repositories;

import com.aybu9.aybualumni.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT User FROM User where User.email = ?1")
    User getByEmail(String email);

    User getByProfileUrl(String profileUrl);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByProfileUrl(String profileUrl);
    

//    @Query(nativeQuery = true)
//    Collection<User> getFriends(Long userId);
}
