package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.job_post.models.JobPost;

public interface JobPostService {

    DataResult<JobPost> create(JobPost jobPost);

    Result delete(Long jobPostId);
}