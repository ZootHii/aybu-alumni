package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.models.UserPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.repositories.UserPostRepository;
import com.google.common.base.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static com.aybu9.aybualumni.core.models.Constants.VISIBILITY_EVERYONE;

@Service
public class UserPostManager implements UserPostService {

    private final UserPostRepository userPostRepository;
    private final AuthService authService;
    private final PostService postService;
    private final FileStorage fileStorage;

    public UserPostManager(UserPostRepository userPostRepository, AuthService authService,
                           PostService postService, FileStorage fileStorage) {
        this.userPostRepository = userPostRepository;
        this.authService = authService;
        this.postService = postService;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<UserPost>> getAll() {
        return new SuccessDataResult<>(userPostRepository.findAll(), "get all success");
    }

    @Override
    public DataResult<Collection<UserPost>> getAllPageable(Integer page, Integer size) {
        return new SuccessDataResult<>(userPostRepository.findAll(PageRequest.of(page, size)).getContent(),
                "get all pageable success");
    }

    @Override
    public DataResult<Collection<UserPost>> getLast3ByUser(Authentication authentication, Long userId) {
        authService.getCurrentUserAccessible(authentication, userId);
        var last3Post = userPostRepository
                .getTopsByOwnerUser(userId, PageRequest.of(0, 3)).getContent();
        return new SuccessDataResult<>(last3Post, "last 3 post get success");
    }

    @Override
    @Transactional
    public DataResult<UserPost> create(Authentication authentication, Long userId,
                                       PostDto postDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);

        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
        var description = postDto.getDescription();
        var visibility = postDto.getVisibility();

        var post = new Post(currentUser);

        if (Strings.isNullOrEmpty(visibility)) {
            visibility = VISIBILITY_EVERYONE;
        }

        if (!Strings.isNullOrEmpty(fileUrl)) {
            post.setFileUrl(fileUrl);
        }

        if (!Strings.isNullOrEmpty(description)) {
            post.setDescription(description);
        }

        var createdPost = postService.create(post).getData();

        var userPost = new UserPost(createdPost, visibility);

        var createdUserPost = userPostRepository.save(userPost);
        return new SuccessDataResult<>(createdUserPost, "user post created success");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId, Long postId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return postService.delete(postId);
    }
}
