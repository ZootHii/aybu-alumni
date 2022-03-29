package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.repositories.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.aybu9.aybualumni.core.result.Constants.DeleteSuccess;

@Service
public class PostManager implements PostService {

    private final PostRepository postRepository;

    public PostManager(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public DataResult<Post> create(Post post) {
        return new SuccessDataResult<>(postRepository.save(post), "post create success");
    }

    @Override
    @Transactional
    public Result delete(Long postId) {
        postRepository.deleteById(postId);
        return new SuccessResult(DeleteSuccess);
    }
}
