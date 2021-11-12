package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;

public interface PageService {

    DataResult<Page> getByPageUrl(String pageUrl);
    
    Boolean existByPageUrl(String pageUrl);
    
    DataResult<CompanyPage> getCompanyByPage(Page page);
    
    DataResult<CompanyPage> getCompanyByPageUrl(String pageUrl);
    
    DataResult<Page> create(Page page);

    DataResult<CompanyPage> createCompany(CompanyPageDto companyPageDto, Long userId);

    DataResult<CommunityPage> createCommunity(CommunityPage communityPage);
}
