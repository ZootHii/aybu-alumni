package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.CommunityPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CommunityPostService {

    DataResult<Collection<CommunityPost>> getAll();

    DataResult<CommunityPost> create(Authentication authentication, Long userId, PostDto postDto,
                                   MultipartFile multipartFile);

    Result delete(Authentication authentication, Long userId, Long postId);
}
