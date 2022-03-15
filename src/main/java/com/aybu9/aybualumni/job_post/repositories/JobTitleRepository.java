package com.aybu9.aybualumni.job_post.repositories;

import com.aybu9.aybualumni.job_post.models.job.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Integer> {
}
