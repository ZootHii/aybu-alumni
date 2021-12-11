package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.user.mappers.UserContactInfoMapper;
import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import com.aybu9.aybualumni.user.repositories.UserContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.aybu9.aybualumni.core.utilities.storage.Constants.FOLDER_NAME_DOCUMENTS;

@Service
public class UserContactInfoManager implements UserContactInfoService{
    
    private final UserContactInfoRepository userContactInfoRepository;
    private final AuthService authService;
    private final FileStorage fileStorage;
    private final UserContactInfoMapper userContactInfoMapper;

    public UserContactInfoManager(UserContactInfoRepository userContactInfoRepository, AuthService authService, 
                                  FileStorage fileStorage, 
                                  @Autowired(required = false) UserContactInfoMapper userContactInfoMapper) {
        this.userContactInfoRepository = userContactInfoRepository;
        this.authService = authService;
        this.fileStorage = fileStorage;
        this.userContactInfoMapper = userContactInfoMapper;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DataResult<UserContactInfo> update(Authentication authentication, Long userId, UserContactInfoDto userContactInfoDto) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var userContactInfoToUpdate = currentUser.getUserContactInfo();
        userContactInfoMapper.updateUserContactInfoFromDto(userContactInfoDto, userContactInfoToUpdate);
        var updatedUserContactInfo = userContactInfoRepository.save(userContactInfoToUpdate);
        return new SuccessDataResult<>(updatedUserContactInfo, "user contact info updated");
    }

    @Override
    @Transactional
    public Result uploadResume(Authentication authentication, Long userId, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var resumeUrl = fileStorage.saveFile(currentUser, multipartFile, FOLDER_NAME_DOCUMENTS);
        var currentUserContactInfo = currentUser.getUserContactInfo();
        currentUserContactInfo.setResumeUrl(resumeUrl);
        userContactInfoRepository.save(currentUserContactInfo);
        return new SuccessResult("resume upload success");
    }
}
