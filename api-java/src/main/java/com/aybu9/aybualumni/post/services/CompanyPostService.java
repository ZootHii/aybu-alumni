package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.CompanyPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CompanyPostService {

    DataResult<Collection<CompanyPost>> getAll();

    DataResult<Collection<CompanyPost>> getAllPageable(Pageable pageable);

    DataResult<Collection<CompanyPost>> getLast3ByCompany(Authentication authentication, Long userId);

    DataResult<CompanyPost> create(Authentication authentication, Long userId, PostDto postDto,
                                    MultipartFile multipartFile);

    Result delete(Authentication authentication, Long userId, Long postId);
}
