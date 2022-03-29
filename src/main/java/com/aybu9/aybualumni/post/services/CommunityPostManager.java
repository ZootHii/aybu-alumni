package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.post.models.CommunityPost;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.repositories.CommunityPostRepository;
import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static com.aybu9.aybualumni.core.models.Constants.VISIBILITY_EVERYONE;

@Service
public class CommunityPostManager implements CommunityPostService {

    private final CommunityPostRepository communityPostRepository;
    private final AuthService authService;
    private final PostService postService;
    private final FileStorage fileStorage;

    public CommunityPostManager(CommunityPostRepository communityPostRepository, AuthService authService,
                              PostService postService, FileStorage fileStorage) {
        this.communityPostRepository = communityPostRepository;
        this.authService = authService;
        this.postService = postService;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CommunityPost>> getAll() {
        return new SuccessDataResult<>(communityPostRepository.findAll(), "get all success");
    }

    @Override
    @Transactional
    public DataResult<CommunityPost> create(Authentication authentication, Long userId,
                                          PostDto postDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCommunityPage = currentUser.getCommunityPage();

        if (currentUserCommunityPage == null) {
            throw new CustomException("current user has no community");
        }

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

        var communityPost = new CommunityPost(createdPost, currentUserCommunityPage, visibility);

        var createdCommunityPost = communityPostRepository.save(communityPost);
        return new SuccessDataResult<>(createdCommunityPost, "community post created success");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId, Long postId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return postService.delete(postId);
    }
}
