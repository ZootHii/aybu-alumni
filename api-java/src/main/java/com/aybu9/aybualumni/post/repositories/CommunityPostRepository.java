package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
}
