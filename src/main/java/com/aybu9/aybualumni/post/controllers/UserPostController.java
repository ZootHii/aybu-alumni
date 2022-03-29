package com.aybu9.aybualumni.post.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.post.models.UserPost;
import com.aybu9.aybualumni.post.models.dtos.PostDto;
import com.aybu9.aybualumni.post.services.UserPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts/user")
public class UserPostController {

    private final UserPostService userPostService;

    public UserPostController(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<UserPost>>> getAll() {
        var result = userPostService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<UserPost>> create(Authentication authentication,
                                                       @PathVariable Long userId,
                                                       @Valid @RequestPart("postDto")
                                                               PostDto postDto,
                                                       @RequestPart(value = "file", required = false)
                                                               MultipartFile multipartFile
    ) {
        var result =
                userPostService.create(authentication, userId, postDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Result> delete(Authentication authentication,
                                         @PathVariable Long userId,
                                         @PathVariable Long postId) {
        var result = userPostService.delete(authentication, userId, postId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
