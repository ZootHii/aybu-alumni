package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import com.aybu9.aybualumni.page.models.dtos.CommunityPageDto;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import com.aybu9.aybualumni.page.repositories.CommunityPageRepository;
import com.aybu9.aybualumni.page.repositories.CompanyPageRepository;
import com.aybu9.aybualumni.page.repositories.PageRepository;
import com.aybu9.aybualumni.sector.services.CommunitySectorService;
import com.aybu9.aybualumni.sector.services.CompanySectorService;
import com.aybu9.aybualumni.user.services.UserService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

import static com.aybu9.aybualumni.page.Constants.COMMUNITY_PAGE_URL;
import static com.aybu9.aybualumni.page.Constants.COMPANY_PAGE_URL;

@Service
public class PageManager implements PageService {

    private final PageRepository pageRepository;
    private final CompanyPageRepository companyPageRepository;
    private final CommunityPageRepository communityPageRepository;
    private final UserService userService;
    private final CompanySectorService companySectorService;
    private final CommunitySectorService communitySectorService;
    private final CityService cityService;
    private final AuthService authService;

    public PageManager(PageRepository pageRepository, CompanyPageRepository companyPageRepository,
                       CommunityPageRepository communityPageRepository, UserService userService, CompanySectorService companySectorService, CommunitySectorService communitySectorService, CityService cityService, AuthService authService) {
        this.pageRepository = pageRepository;
        this.companyPageRepository = companyPageRepository;
        this.communityPageRepository = communityPageRepository;
        this.userService = userService;
        this.companySectorService = companySectorService;
        this.communitySectorService = communitySectorService;
        this.cityService = cityService;
        this.authService = authService;
    }

    @Override
    @Transactional
    public DataResult<Page> create(Page page) {
        // public olmaktan çıkarabiliriz gerek yok
        // gelen url ile başka bir page var mı yok mu kontrol et
        var uniqueName = Arrays.stream(page.getPageUrl().split("/"))
                .reduce((first, second) -> second).orElseThrow();
        var existByPageUrlUniqueName = existsByPageUrlIsEndingWith("/" + uniqueName);
        if (existByPageUrlUniqueName) {
            throw new CustomException("there is already a page with this page url");
        }
        return new SuccessDataResult<>(pageRepository.save(page), "page created");
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

    @Override
    public Boolean existsByPageUrlIsEndingWith(String uniqueName) {
        return pageRepository.existsByPageUrlIsEndingWith(uniqueName);
    }

    @Override
    public DataResult<Collection<CompanyPage>> getAllCompanyPageable(Pageable pageable) {
        var companyPages = companyPageRepository.findAll(pageable).getContent();
        return new SuccessDataResult<>(companyPages, "get all pageable success");
    }

    @Override
    public DataResult<CompanyPage> getCompanyByPageUrl(String pageUrl) {
        var page = getByPageUrl(pageUrl).getData();
        var companyPage = companyPageRepository.getByPage(page);
        return new SuccessDataResult<>(companyPage, "found company page by page url");
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
    @Transactional
    public DataResult<CompanyPage> createCompany(Authentication authentication, Long userId,
                                                 CompanyPageDto companyPageDto) {
        // userId al ve user ı bul
        // companypagedto oluştur
        // page ve companypage içerisindeki gerekli bilgileri al dto için
        // dto içerisinden sector id al ve sectorleri kontrol et var mı yok mu kontrol işini companysector manager de exception throw ederek yap
        // alınan bilgileri page ve company page e set et ve oluştur
        // önce page oluştur oluşan page i company page e set ederek oluştur

        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var companySectorId = companyPageDto.getCompanySectorId();
        var cityId = companyPageDto.getCityId();
        var companySector = companySectorService.get(companySectorId).getData();
        var city = cityService.get(cityId).getData();

        var name = companyPageDto.getName();
        var pageUrl = String.format("%s/%s", COMPANY_PAGE_URL, companyPageDto.getPageUrl());
        var websiteUrl = companyPageDto.getWebsiteUrl();
        var slogan = companyPageDto.getSlogan();

        var page = new Page(name, currentUser, pageUrl);

        if (!Strings.isNullOrEmpty(websiteUrl)) {
            page.setWebsiteUrl(websiteUrl);
        }
        if (!Strings.isNullOrEmpty(slogan)) {
            page.setSlogan(slogan);
        }

        var createdPage = create(page).getData();
        var companyPage = new CompanyPage(createdPage, companySector, city);
        var createdCompanyPage = companyPageRepository.save(companyPage);
        return new SuccessDataResult<>(createdCompanyPage,
                "company page created for " + currentUser.getEmail());
    }

    @Override
    public DataResult<Collection<CommunityPage>> getAllCommunityPageable(Pageable pageable) {
        return new SuccessDataResult<>(communityPageRepository.findAll(pageable).getContent(),
                "get all pageable success");
    }

    @Override
    public DataResult<CommunityPage> getCommunityByPageUrl(String pageUrl) {
        var page = getByPageUrl(pageUrl).getData();
        var communityPage = communityPageRepository.getByPage(page);
        return new SuccessDataResult<>(communityPage, "found community page by page url");
    }

    @Override
    public DataResult<CommunityPage> getCommunityByPage(Page page) {
        return null;
    }

    @Override
    @Transactional
    public DataResult<CommunityPage> createCommunity(Authentication authentication, Long userId,
                                                     CommunityPageDto communityPageDto) {

        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var communitySectorId = communityPageDto.getCommunitySectorId();
        var communitySector = communitySectorService.get(communitySectorId).getData();

        var name = communityPageDto.getName();
        var pageUrl = String.format("%s/%s", COMMUNITY_PAGE_URL, communityPageDto.getPageUrl());
        var websiteUrl = communityPageDto.getWebsiteUrl();
        var slogan = communityPageDto.getSlogan();

        var page = new Page(name, currentUser, pageUrl);

        if (!Strings.isNullOrEmpty(websiteUrl)) {
            page.setWebsiteUrl(websiteUrl);
        }
        if (!Strings.isNullOrEmpty(slogan)) {
            page.setSlogan(slogan);
        }

        var createdPage = create(page).getData();
        var communityPage = new CommunityPage(createdPage, communitySector);
        var createdCommunityPage = communityPageRepository.save(communityPage);
        return new SuccessDataResult<>(createdCommunityPage,
                "community page created for " + currentUser.getEmail());
    }
}
