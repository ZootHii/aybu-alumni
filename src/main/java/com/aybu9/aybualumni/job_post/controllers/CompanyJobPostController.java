package com.aybu9.aybualumni.job_post.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.job_post.models.CompanyJobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CompanyJobPostDto;
import com.aybu9.aybualumni.job_post.services.CompanyJobPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/job-posts/company")
public class CompanyJobPostController {

    private final CompanyJobPostService companyJobPostService;

    public CompanyJobPostController(CompanyJobPostService companyJobPostService) {
        this.companyJobPostService = companyJobPostService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CompanyJobPost>>> getAll() {
        var result = companyJobPostService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CompanyJobPost>> create(Authentication authentication,
                                                           @PathVariable Long userId,
                                                           @Valid @RequestPart("companyJobPostDto")
                                                                         CompanyJobPostDto companyJobPostDto,
                                                           @RequestPart(value = "file", required = false)
                                                                   MultipartFile multipartFile
    ) {
        var result =
                companyJobPostService.create(authentication, userId, companyJobPostDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}