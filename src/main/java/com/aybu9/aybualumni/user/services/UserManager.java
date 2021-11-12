package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.auth.services.AuthManager;
import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.core.storage.FileStorage;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Objects;

import static com.aybu9.aybualumni.core.result.Constants.*;
import static com.aybu9.aybualumni.core.storage.Constants.FOLDER_NAME_IMAGES;
import static com.aybu9.aybualumni.core.storage.Constants.STORAGE_BASE_URL;

@Service
public class UserManager implements UserService {

    Logger logger = LoggerFactory.getLogger(UserManager.class);

    private final UserRepository userRepository;
    private final FileStorage fileStorage;
    private final AuthService authService;
    
    @Autowired
    @Lazy
    public UserManager(UserRepository userRepository, FileStorage fileStorage, AuthService authService) {
        this.userRepository = userRepository;
        this.fileStorage = fileStorage;
        this.authService = authService;
    }

    @Override
    public DataResult<Collection<User>> getAll() {
        return new SuccessDataResult<>(userRepository.findAll(), GetAllSuccess);
    }

    @Override
    public DataResult<User> get(Long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new CustomException("User not found");
        }
        return new SuccessDataResult<>(user.get(), GetSuccess);
    }

    @Override
    public DataResult<User> getByProfileUrl(String profileUrl) {
        var user = userRepository.getByProfileUrl(profileUrl);
        if (user == null) {
            throw new CustomException("User not found by profile url " + profileUrl);
        }
        return new SuccessDataResult<>(user, "found user by profile url");
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        var user = userRepository.getByEmail(email);
        if (user == null) {
            throw new CustomException("User not found by email " + email);
        }
        return new SuccessDataResult<>(user, "found by email");
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
        return new SuccessDataResult<>(userRepository.save(user), "user created");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId) {
        var currentUser = authService.getCurrentUser(authentication);
        if (!Objects.equals(currentUser.getId(), userId)){
            throw new AccessDeniedException("Forbidden Access Denied");
        }
        try {
            userRepository.deleteById(userId);
        } catch (Exception exception) {
            throw new CustomException("user delete error " + exception.getMessage());
        }
        return new SuccessResult(DeleteSuccess);
    }

    @Override
    @Transactional
    public Result updateProfilePhoto(Authentication authentication, Long userId, MultipartFile multipartFile) {
        // check image is not empty
        // check file is an image
        // check user exist
        // get metadata of the file
        // maybe we can crop the image 200x200
        var currentUser = authService.getCurrentUser(authentication);
        if (!Objects.equals(currentUser.getId(), userId)){
            throw new AccessDeniedException("Forbidden Access Denied");
        }
        //var userDataResult = get(userId);
        //var user = userDataResult.getData();
        var profilePhotoUrl = savePhoto(currentUser, multipartFile);
        currentUser.setProfilePhotoUrl(profilePhotoUrl);
        userRepository.save(currentUser);
        return new SuccessResult("profile photo update success");
    }

    @Override
    @Transactional
    public Result updateCoverPhoto(Authentication authentication, Long userId, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUser(authentication);
        if (!Objects.equals(currentUser.getId(), userId)){
            throw new AccessDeniedException("Forbidden Access Denied");
        }
        
        //var userDataResult = get(userId);
        //var user = userDataResult.getData();
        var coverPhotoUrl = savePhoto(currentUser, multipartFile);
        currentUser.setCoverPhotoUrl(coverPhotoUrl);
        userRepository.save(currentUser);
        return new SuccessResult("cover photo update success");
    }

    private String savePhoto(User user, MultipartFile multipartFile) {
        fileStorage.checkIfEmptyOrNull(multipartFile);
        fileStorage.checkIfImage(multipartFile);
        var path = fileStorage.createPath(FOLDER_NAME_IMAGES, user.getId());
        var fileName = fileStorage.createFileName(multipartFile);
        var metadata = fileStorage.createMetadata(multipartFile);
        var inputStream = fileStorage.getInputStream(multipartFile);
        fileStorage.save(path, fileName, inputStream, multipartFile, metadata);
        return String.format("%s/%s/%s/%s", STORAGE_BASE_URL, FOLDER_NAME_IMAGES, user.getId(), fileName);
    }
//    @Override
//    public DataResult<byte[]> downloadProfilePhoto(Long userId) {
//        var userDataResult = get(userId);
//        if (!userDataResult.isSuccess()){
//            return new ErrorDataResult<>(userDataResult.getMessage());
//        }
//        var user = userDataResult.getData();
//        var path = fileStorage.createPath(FOLDER_NAME_IMAGES, userId);
//        var key = user.getProfilePhotoUrl();
//        var image = fileStorage.download(path, key);
//        return new SuccessDataResult<>(image, "profile photo download success");
//    }
}