package com.aybu9.aybualumni.job_post.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.job_post.models.CommunityJobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CommunityJobPostDto;
import com.aybu9.aybualumni.job_post.services.CommunityJobPostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/job-posts/community")
public class CommunityJobPostController {

    private final CommunityJobPostService communityJobPostService;

    public CommunityJobPostController(CommunityJobPostService communityJobPostService) {
        this.communityJobPostService = communityJobPostService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CommunityJobPost>>> getAll() {
        var result = communityJobPostService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pageable/{page}/{size}")
    public ResponseEntity<DataResult<Collection<CommunityJobPost>>> getAllPageable(@PathVariable Integer page,
                                                                           @PathVariable Integer size) {
        var result = communityJobPostService.getAllPageable(PageRequest.of(page, size));
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CommunityJobPost>> create(Authentication authentication,
                                                               @PathVariable Long userId,
                                                               @Valid @RequestPart("communityJobPostDto")
                                                                       CommunityJobPostDto communityJobPostDto,
                                                               @RequestPart(value = "file", required = false)
                                                                       MultipartFile multipartFile
    ) {
        var result =
                communityJobPostService.create(authentication, userId, communityJobPostDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{jobPostId}")
    public ResponseEntity<Result> delete(Authentication authentication,
                                         @PathVariable Long userId,
                                         @PathVariable Long jobPostId) {
        var result = communityJobPostService.delete(authentication, userId, jobPostId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}