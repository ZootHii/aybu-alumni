package com.aybu9.aybualumni.job_post.repositories;

import com.aybu9.aybualumni.job_post.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
