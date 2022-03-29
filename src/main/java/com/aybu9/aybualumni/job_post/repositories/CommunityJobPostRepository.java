package com.aybu9.aybualumni.job_post.repositories;

import com.aybu9.aybualumni.job_post.models.CommunityJobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJobPostRepository extends JpaRepository<CommunityJobPost, Long> {
}
