package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserContactInfoService {
    
    DataResult<UserContactInfo> create(UserContactInfo userContactInfo);

    DataResult<UserContactInfo> update(Authentication authentication, Long userId, UserContactInfoDto userContactInfoDto);

    Result uploadResume(Authentication authentication, Long userId, MultipartFile multipartFile);
}
