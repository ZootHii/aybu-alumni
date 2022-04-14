package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.page.models.dtos.CommunityPageDto;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import org.springframework.security.core.Authentication;

public interface PageService {

    DataResult<Page> create(Page page);

    DataResult<Page> getByPageUrl(String pageUrl);

    Boolean existByPageUrl(String pageUrl);

    Boolean existsByPageUrlIsEndingWith(String uniqueName);

    DataResult<CompanyPage> createCompany(Authentication authentication, CompanyPageDto companyPageDto, Long userId);

    DataResult<CompanyPage> getCompanyByPage(Page page);

    DataResult<CompanyPage> getCompanyByPageUrl(String pageUrl);

    DataResult<CommunityPage> createCommunity(Authentication authentication, CommunityPageDto communityPageDto, Long userId);

    DataResult<CommunityPage> getCommunityByPage(Page page);

    DataResult<CommunityPage> getCommunityByPageUrl(String pageUrl);
}
