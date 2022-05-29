package com.aybu9.aybualumni.page.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.dtos.CommunityPageDto;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import com.aybu9.aybualumni.page.services.PageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/company/pageable/{page}/{size}")
    public ResponseEntity<DataResult<Collection<CompanyPage>>> getAllCompanyPageable(@PathVariable Integer page,
                                                                                     @PathVariable Integer size) {
        var result = pageService.getAllCompanyPageable(PageRequest.of(page, size));
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/company/{uniqueName}")
    public ResponseEntity<DataResult<CompanyPage>> getCompany(@PathVariable String uniqueName,
                                                              HttpServletRequest request) {

        var pageUrl = request.getRequestURL().toString();
        var result = pageService.getCompanyByPageUrl(pageUrl);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("{userId}/company")
    public ResponseEntity<DataResult<CompanyPage>> createCompany(Authentication authentication,
                                                                 @PathVariable Long userId,
                                                                 @Valid @RequestBody CompanyPageDto companyPageDto) {
        var result = pageService.createCompany(authentication, userId, companyPageDto);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/community/pageable/{page}/{size}")
    public ResponseEntity<DataResult<Collection<CommunityPage>>> getAllCommunityPageable(@PathVariable Integer page,
                                                                                         @PathVariable Integer size) {
        var result = pageService.getAllCommunityPageable(PageRequest.of(page, size));
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/community/{uniqueName}")
    public ResponseEntity<DataResult<CommunityPage>> getCommunity(@PathVariable String uniqueName,
                                                                  HttpServletRequest request) {

        var pageUrl = request.getRequestURL().toString();
        var result = pageService.getCommunityByPageUrl(pageUrl);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("{userId}/community")
    public ResponseEntity<DataResult<CommunityPage>> createCommunity(Authentication authentication,
                                                                     @PathVariable Long userId,
                                                                     @Valid @RequestBody CommunityPageDto communityPageDto) {
        var result = pageService.createCommunity(authentication, userId, communityPageDto);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
