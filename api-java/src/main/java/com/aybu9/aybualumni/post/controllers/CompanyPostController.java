package com.aybu9.aybualumni.post.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.CompanyPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.services.CompanyPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts/company")
public class CompanyPostController {

    private final CompanyPostService companyPostService;

    public CompanyPostController(CompanyPostService companyPostService) {
        this.companyPostService = companyPostService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CompanyPost>>> getAll() {
        var result = companyPostService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CompanyPost>> create(Authentication authentication,
                                                          @PathVariable Long userId,
                                                          @Valid @RequestPart("postDto")
                                                                  PostDto postDto,
                                                          @RequestPart(value = "file", required = false)
                                                                  MultipartFile multipartFile
    ) {
        var result =
                companyPostService.create(authentication, userId, postDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Result> delete(Authentication authentication,
                                         @PathVariable Long userId,
                                         @PathVariable Long postId) {
        var result = companyPostService.delete(authentication, userId, postId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
