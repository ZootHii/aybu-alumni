package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashSet;

import static com.aybu9.aybualumni.core.result.Constants.*;

@Service
public class UserManager implements UserService {

    Logger logger = LoggerFactory.getLogger(UserManager.class);

    private final UserRepository userRepository;
    private final FileStorage fileStorage;
    private final AuthService authService;
    private final UserContactInfoService userContactInfoService;

    @Autowired
    @Lazy
    public UserManager(UserRepository userRepository, FileStorage fileStorage, AuthService authService,
                       UserContactInfoService userContactInfoService) {
        this.userRepository = userRepository;
        this.fileStorage = fileStorage;
        this.authService = authService;
        this.userContactInfoService = userContactInfoService;
    }

    @Override
    public DataResult<Collection<User>> getAll() {
        return new SuccessDataResult<>(userRepository.findAll(Sort.by("id")), GetAllSuccess);
    }

    @Override
    public DataResult<User> get(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));
        return new SuccessDataResult<>(user, GetSuccess);
    }

    @Override
    public DataResult<User> getByProfileUrl(String profileUrl) {
        var user = userRepository.getByProfileUrl(profileUrl)
                .orElseThrow(() -> new CustomException("User not found by profile url " + profileUrl));
        return new SuccessDataResult<>(user, "found user by profile url");
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        var user = userRepository.getByEmail(email)
                .orElseThrow(() -> new CustomException("User not found by email " + email));
        return new SuccessDataResult<>(user, "found by email");
    }

    @Override
    public DataResult<Collection<User>> getAllByIdIn(Collection<Long> ids) {
        // TODO
        var usersSet = userRepository.getAllByIdIn(new HashSet<>(ids));
        return new SuccessDataResult<>(usersSet, "gell all by id in");
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByProfileUrl(String profileUrl) {
        return userRepository.existsByProfileUrl(profileUrl);
    }

    @Override
    @Transactional
    public DataResult<User> create(User user) {
        var existByProfileUrl = existsByProfileUrl(user.getProfileUrl());
        if (existByProfileUrl) {
            throw new CustomException("there is already a user with this profile url");
        }
        var existsByEmail = existsByEmail(user.getEmail());
        if (existsByEmail) {
            throw new CustomException("there is already a user with this email");
        }
        var createdUserContactInfo = userContactInfoService.create(new UserContactInfo(user.getEmail())).getData();
        user.setUserContactInfo(createdUserContactInfo);
        return new SuccessDataResult<>(userRepository.save(user), "user created");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DataResult<User> updateSave(User user) {
        return new SuccessDataResult<>(userRepository.save(user), "save update success");
    }

    @Override
    @Transactional
    public DataResult<String> updateAbout(Authentication authentication, Long userId, String about) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        currentUser.setAbout(about);
        return new SuccessDataResult<>(about, "update user about");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId) {
        authService.getCurrentUserAccessible(authentication, userId);
        try {
            userRepository.deleteById(userId);
        } catch (Exception exception) {
            throw new CustomException("user delete error " + exception.getMessage());
        }
        return new SuccessResult(DeleteSuccess);
    }

    @Override
    @Transactional
    public Result uploadProfileImage(Authentication authentication, Long userId, MultipartFile multipartFile) {
        // check image is not empty
        // check file is an image
        // check user exist
        // get metadata of the file
        // maybe we can crop the image 200x200
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var profilePhotoUrl = fileStorage.saveImageFile(currentUser, multipartFile);
        currentUser.setProfilePhotoUrl(profilePhotoUrl);
        userRepository.save(currentUser);
        return new SuccessResult("profile photo upload success");
    }

    @Override
    @Transactional
    public Result uploadCoverImage(Authentication authentication, Long userId, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var coverPhotoUrl = fileStorage.saveImageFile(currentUser, multipartFile);
        currentUser.setCoverPhotoUrl(coverPhotoUrl);
        userRepository.save(currentUser);
        return new SuccessResult("cover photo upload success");
    }
}
