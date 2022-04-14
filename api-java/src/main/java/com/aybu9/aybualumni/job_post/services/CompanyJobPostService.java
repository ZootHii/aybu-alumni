package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.job_post.models.CompanyJobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CompanyJobPostDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CompanyJobPostService {

    DataResult<Collection<CompanyJobPost>> getAll();

    DataResult<CompanyJobPost> create(Authentication authentication, Long userId, CompanyJobPostDto companyJobPostDto,
                                      MultipartFile multipartFile);

    Result delete(Authentication authentication, Long userId, Long jobPostId);
}
