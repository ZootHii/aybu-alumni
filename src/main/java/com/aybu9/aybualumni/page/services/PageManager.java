package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import com.aybu9.aybualumni.page.repositories.CommunityPageRepository;
import com.aybu9.aybualumni.page.repositories.CompanyPageRepository;
import com.aybu9.aybualumni.page.repositories.PageRepository;
import com.aybu9.aybualumni.user.services.UserService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageManager implements PageService {

    private final PageRepository pageRepository;
    private final CompanyPageRepository companyPageRepository;
    private final CommunityPageRepository communityPageRepository;
    private final UserService userService;
    private final CompanySectorService companySectorService;
    private final CityService cityService;

    public PageManager(PageRepository pageRepository, CompanyPageRepository companyPageRepository,
                       CommunityPageRepository communityPageRepository, UserService userService, CompanySectorService companySectorService, CityService cityService) {
        this.pageRepository = pageRepository;
        this.companyPageRepository = companyPageRepository;
        this.communityPageRepository = communityPageRepository;
        this.userService = userService;
        this.companySectorService = companySectorService;
        this.cityService = cityService;
    }

    @Override
    public DataResult<Page> getByPageUrl(String pageUrl) {
        var page = pageRepository.getByPageUrl(pageUrl);
        if (page == null) {
            throw new CustomException("Page not found by page url " + pageUrl);
        }
        return new SuccessDataResult<>(page, "page found by page url");
    }

    @Override
    public Boolean existByPageUrl(String pageUrl) {
        return pageRepository.existsByPageUrl(pageUrl);
    }

    @Override // todo şimdilik gereksiz
    public DataResult<CompanyPage> getCompanyByPage(Page page) {
        var companyPage = companyPageRepository.getByPage(page);
        if (companyPage == null) {
            throw new CustomException("Page not found by page " + page.getName());
        }
        return new SuccessDataResult<>(companyPage, "company page found by page");
    }

    @Override
    public DataResult<CompanyPage> getCompanyByPageUrl(String pageUrl) {
        var page = getByPageUrl(pageUrl).getData();
        var companyPage = companyPageRepository.getByPage(page);
        return new SuccessDataResult<>(companyPage, "found company page by page url");
    }

    @Override
    @Transactional
    public DataResult<Page> create(Page page) {
        // public olmaktan çıkarabiliriz gerek yok
        // gelen url ile başka bir page var mı yok mu kontrol et
        var existByPageUrl = existByPageUrl(page.getPageUrl());
        if (existByPageUrl){
            throw new CustomException("there is already a page with this page url");
        }
        return new SuccessDataResult<>(pageRepository.save(page), "page created");
    }

    @Override
    @Transactional // biri bitmeden bir sorun çıkarsa diğerini geri alıyor
    public DataResult<CompanyPage> createCompany(CompanyPageDto companyPageDto, Long userId) { // TODO şehirler eklenebilir
        // userId al ve user ı bul
        // companypagedto oluştur
        // page ve companypage içerisindeki gerekli bilgileri al dto için
        // dto içerisinden sector id al ve sectorleri kontrol et var mı yok mu kontrol işini companysector manager de exception throw ederek yap
        // alınan bilgileri page ve company page e set et ve oluştur
        // önce page oluştur oluşan page i company page e set ederek oluştur

        var ownerUser = userService.get(userId).getData();
        var companySectorId = companyPageDto.getCompanySectorId();
        var cityId = companyPageDto.getCityId();
        var companySector = companySectorService.get(companySectorId).getData();
        var city = cityService.get(cityId).getData();
        
        var name = companyPageDto.getName();
        var pageUrl = String.format("%s/%s", "http://localhost:4024/pages/company", companyPageDto.getPageUrl());
        var websiteUrl = companyPageDto.getWebsiteUrl();
        var slogan = companyPageDto.getSlogan();
        
        var page = new Page(name, ownerUser, pageUrl);
        
        if (!Strings.isNullOrEmpty(websiteUrl)) {
            page.setWebsiteUrl(websiteUrl);
        }
        if (!Strings.isNullOrEmpty(slogan)){
            page.setSlogan(slogan);
        }
        
        var createdPage = create(page).getData();
        var companyPage = new CompanyPage(createdPage, companySector, city);
        var createdCompanyPage = companyPageRepository.save(companyPage);
        return new SuccessDataResult<>(createdCompanyPage, "company page created for " + ownerUser.getEmail());
    }

    @Override
    public DataResult<CommunityPage> createCommunity(CommunityPage communityPage) {
        return null;
    }
}
