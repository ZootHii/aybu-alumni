package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
