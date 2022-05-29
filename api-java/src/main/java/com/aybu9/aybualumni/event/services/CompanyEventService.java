package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.event.models.CompanyEvent;
import com.aybu9.aybualumni.event.models.dtos.CompanyEventDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CompanyEventService {

    DataResult<Collection<CompanyEvent>> getAll();

    DataResult<Collection<CompanyEvent>> getAllPageable(Pageable pageable);

    DataResult<CompanyEvent> create(Authentication authentication, Long userId, CompanyEventDto companyEventDto,
                                    MultipartFile multipartFile);
}
