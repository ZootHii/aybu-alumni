package com.aybu9.aybualumni.page.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.dtos.CompanyPageDto;
import com.aybu9.aybualumni.page.services.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/pages")
@CrossOrigin
public class PageController {
    
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("{userId}/company")
    public ResponseEntity<DataResult<CompanyPage>> createCompany(@Valid @RequestBody CompanyPageDto companyPageDto, 
                                                                 @PathVariable Long userId){ // todo auth
        var result = pageService.createCompany(companyPageDto, userId);
        if (!result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/company/{uniqueName}")
    public ResponseEntity<DataResult<CompanyPage>> getCompany(@PathVariable String uniqueName, 
                                                              HttpServletRequest request){
        var pageUrl = request.getRequestURL().toString();
        
        // todo yada ihtiyaca göre karar ver
        var pageUrl2 = String.format("%s/%s", "http://localhost:4024/pages/company", uniqueName);
        
        var result = pageService.getCompanyByPageUrl(pageUrl);
        if (!result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}