package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.models.dtos.UpdateUserProfileDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UserService {

    DataResult<Collection<User>> getAll();

    DataResult<User> get(Long userId);

    DataResult<User> getByProfileUrl(String profileUrl);

    DataResult<User> getByEmail(String email);

    DataResult<Collection<User>> getAllByIdIn(Collection<Long> ids);

    Boolean existsByEmail(String email);

    Boolean existsByProfileUrl(String profileUrl);

    DataResult<User> create(User user);

    DataResult<User> updateSave(User user);

    Result delete(Authentication authentication, Long userId);

    DataResult<String> updateAbout(Authentication authentication, Long userId, String about);

    DataResult<User> updateUserProfile(Authentication authentication, Long userId,
                                       UpdateUserProfileDto updateUserProfileDto);

    // Result updatePassword() todo dto old and new password al userid al kontrol et eski doğruysaa değiştir çıkış yap

    Result uploadProfileImage(Authentication authentication, Long userId, MultipartFile multipartFile);

    Result uploadCoverImage(Authentication authentication, Long userId, MultipartFile multipartFile);
}
