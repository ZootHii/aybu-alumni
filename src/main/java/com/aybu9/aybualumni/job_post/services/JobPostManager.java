package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.job_post.models.JobPost;
import com.aybu9.aybualumni.job_post.repositories.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobPostManager implements JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostManager(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    @Transactional
    public DataResult<JobPost> create(JobPost jobPost) {
        return new SuccessDataResult<>(jobPostRepository.save(jobPost), "job post create success");
    }
}
