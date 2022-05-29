package com.aybu9.aybualumni.post.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.post.models.CompanyPost;
import com.aybu9.aybualumni.post.models.Post;
import com.aybu9.aybualumni.post.models.UserPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.repositories.CompanyPostRepository;
import com.google.common.base.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static com.aybu9.aybualumni.core.models.Constants.VISIBILITY_EVERYONE;

@Service
public class CompanyPostManager implements CompanyPostService {

    private final CompanyPostRepository companyPostRepository;
    private final AuthService authService;
    private final PostService postService;
    private final FileStorage fileStorage;

    public CompanyPostManager(CompanyPostRepository companyPostRepository, AuthService authService,
                              PostService postService, FileStorage fileStorage) {
        this.companyPostRepository = companyPostRepository;
        this.authService = authService;
        this.postService = postService;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CompanyPost>> getAll() {
        return new SuccessDataResult<>(companyPostRepository.findAll(), "get all success");
    }

    @Override
    public DataResult<Collection<CompanyPost>> getAllPageable(Pageable pageable) {
        return new SuccessDataResult<>(companyPostRepository.findAll(pageable).getContent(),
                "get all pageable success");
    }

    @Override
    public DataResult<Collection<CompanyPost>> getLast3ByCompany(Authentication authentication, Long userId) {
        var user = authService.getCurrentUserAccessible(authentication, userId);
        var companyPageId = user.getCompanyPage().getId();
        var last3Post = companyPostRepository
                .getTopsByOwnerCompanyPage(companyPageId, PageRequest.of(0, 3)).getContent();
        return new SuccessDataResult<>(last3Post, "last 3 post get success");
    }

    @Override
    @Transactional
    public DataResult<CompanyPost> create(Authentication authentication, Long userId,
                                          PostDto postDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCompanyPage = currentUser.getCompanyPage();

        if (currentUserCompanyPage == null) {
            throw new CustomException("current user has no company");
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

        var companyPost = new CompanyPost(createdPost, currentUserCompanyPage, visibility);

        var createdCompanyPost = companyPostRepository.save(companyPost);
        return new SuccessDataResult<>(createdCompanyPost, "company post created success");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId, Long postId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return postService.delete(postId);
    }
}
