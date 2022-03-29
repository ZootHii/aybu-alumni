package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.job_post.models.CommunityJobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CommunityJobPostDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CommunityJobPostService {

    DataResult<Collection<CommunityJobPost>> getAll();

    DataResult<CommunityJobPost> create(Authentication authentication, Long userId, CommunityJobPostDto communityJobPostDto,
                                        MultipartFile multipartFile);

    Result delete(Authentication authentication, Long userId, Long jobPostId);
}
