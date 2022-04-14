package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.user.One;
import com.aybu9.aybualumni.user.mappers.UserContactInfoMapper;
import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import com.aybu9.aybualumni.user.repositories.UserContactInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserContactInfoManager implements UserContactInfoService{
    
    private final UserContactInfoRepository userContactInfoRepository;
    private final AuthService authService;
    private final FileStorage fileStorage;
    public UserContactInfoManager(UserContactInfoRepository userContactInfoRepository, AuthService authService, 
                                  FileStorage fileStorage) {
        this.userContactInfoRepository = userContactInfoRepository;
        this.authService = authService;
        this.fileStorage = fileStorage;
    }

    @Override
    @Transactional
    public DataResult<UserContactInfo> create(UserContactInfo userContactInfo) {
        var createdUserContactInfo = userContactInfoRepository.save(userContactInfo);
        return new SuccessDataResult<>(createdUserContactInfo, "user contact info created");
    }

    // https://www.baeldung.com/spring-data-partial-update#1-mapping-strategy
    // https://www.baeldung.com/mapstruct
    @Override
    @Transactional(/*propagation = Propagation.REQUIRES_NEW*/)
    public DataResult<UserContactInfo> update(Authentication authentication, Long userId, UserContactInfoDto userContactInfoDto) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var userContactInfoToUpdate = currentUser.getUserContactInfo();
        //userContactInfoMapper.updateUserContactInfoFromUserContactInfoDto(userContactInfoDto, userContactInfoToUpdate);
        UserContactInfoMapper.INSTANCE.updateUserContactInfoFromUserContactInfoDto(userContactInfoDto, userContactInfoToUpdate);
        var one = new One();
        one.setId(1);
        one.setCode("tt");
        var oneDto= UserContactInfoMapper.INSTANCE.createOne(one);
        var updatedUserContactInfo = userContactInfoRepository.save(userContactInfoToUpdate);
        return new SuccessDataResult<>(updatedUserContactInfo, "user contact info updated");
    }

    @Override
    @Transactional
    public Result uploadResume(Authentication authentication, Long userId, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var resumeUrl = fileStorage.saveDocumentFile(currentUser, multipartFile);
        var currentUserContactInfo = currentUser.getUserContactInfo();
        currentUserContactInfo.setResumeUrl(resumeUrl);
        userContactInfoRepository.save(currentUserContactInfo);
        return new SuccessResult("resume upload success");
    }
}
