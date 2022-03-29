package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.Post;

public interface PostService {
    
    DataResult<Post> create(Post post);

    Result delete(Long postId);
}
