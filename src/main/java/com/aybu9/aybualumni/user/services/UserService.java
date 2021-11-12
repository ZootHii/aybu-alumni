package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UserService {
    
    DataResult<Collection<User>> getAll();

    DataResult<User> get(Long userId);

    DataResult<User> getByProfileUrl(String profileUrl);

    DataResult<User> getByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByProfileUrl(String profileUrl);

    DataResult<User> create(User user);

    Result delete(Authentication authentication, Long userId);
    
    Result updateProfilePhoto(Authentication authentication, Long userId, MultipartFile multipartFile);

    Result updateCoverPhoto(Authentication authentication, Long userId, MultipartFile multipartFile);
    
//    DataResult<byte[]> downloadProfilePhoto(Long userId);
}
