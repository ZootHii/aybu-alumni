package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.UserPost;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {

    @Query("select up from UserPost up where up.ownerUser.id = :userId order by up.id desc")
    Page<UserPost> getTopsByOwnerUser(@Param("userId") Long userId, Pageable pageable);
}

