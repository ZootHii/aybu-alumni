package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.event.models.CommunityEvent;
import com.aybu9.aybualumni.event.models.dtos.CommunityEventDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CommunityEventService {

    DataResult<Collection<CommunityEvent>> getAll();

    DataResult<Collection<CommunityEvent>> getAllPageable(Pageable pageable);

    DataResult<CommunityEvent> create(Authentication authentication, Long userId, CommunityEventDto communityEventDto,
                                      MultipartFile multipartFile);
}