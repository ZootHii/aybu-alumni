package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.page.models.dtos.CommunityPageDto;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface PageService {

    DataResult<Page> create(Page page);

    DataResult<Page> getByPageUrl(String pageUrl);

    Boolean existByPageUrl(String pageUrl);

    Boolean existsByPageUrlIsEndingWith(String uniqueName);

    DataResult<Collection<CompanyPage>> getAllCompanyPageable(Pageable pageable);

    DataResult<CompanyPage> getCompanyByPageUrl(String pageUrl);

    DataResult<CompanyPage> getCompanyByPage(Page page);

    DataResult<CompanyPage> createCompany(Authentication authentication, Long userId,
                                          CompanyPageDto companyPageDto);

    DataResult<Collection<CommunityPage>> getAllCommunityPageable(Pageable pageable);

    DataResult<CommunityPage> getCommunityByPageUrl(String pageUrl);

    DataResult<CommunityPage> getCommunityByPage(Page page);

    DataResult<CommunityPage> createCommunity(Authentication authentication, Long userId,
                                              CommunityPageDto communityPageDto);


}
