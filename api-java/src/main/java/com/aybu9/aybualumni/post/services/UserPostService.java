package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.UserPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UserPostService {

    DataResult<Collection<UserPost>> getAll();

    DataResult<Collection<UserPost>> getAllPageable(Integer page, Integer size);

    DataResult<Collection<UserPost>> getLast3ByUser(Authentication authentication, Long userId);

    DataResult<UserPost> create(Authentication authentication, Long userId, PostDto postDto,
                                    MultipartFile multipartFile);

    Result delete(Authentication authentication, Long userId, Long postId);
}
