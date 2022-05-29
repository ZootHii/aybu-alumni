package com.aybu9.aybualumni.post.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.CommunityPost;
import com.aybu9.aybualumni.post.models.UserPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.services.CommunityPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts/community")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    public CommunityPostController(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CommunityPost>>> getAll() {
        var result = communityPostService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/last3")
    public ResponseEntity<DataResult<Collection<CommunityPost>>> getLast3ByCommunity(Authentication authentication,
                                                                           @PathVariable Long userId) {
        var result = communityPostService.getLast3ByCommunity(authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CommunityPost>> create(Authentication authentication,
                                                            @PathVariable Long userId,
                                                            @Valid @RequestPart("postDto")
                                                                  PostDto postDto,
                                                            @RequestPart(value = "file", required = false)
                                                                  MultipartFile multipartFile
    ) {
        var result =
                communityPostService.create(authentication, userId, postDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Result> delete(Authentication authentication,
                                         @PathVariable Long userId,
                                         @PathVariable Long postId) {
        var result = communityPostService.delete(authentication, userId, postId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
