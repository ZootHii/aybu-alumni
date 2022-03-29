//package com.aybu9.aybualumni.job_post.controllers;
//
//import com.aybu9.aybualumni.core.result.DataResult;
//import com.aybu9.aybualumni.core.result.Result;
//import com.aybu9.aybualumni.job_post.models.UserJobPost;
//import com.aybu9.aybualumni.job_post.models.dtos.UserJobPostDto;
//import com.aybu9.aybualumni.job_post.services.UserJobPostService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.util.Collection;
//
//@RestController
//@RequestMapping("/api/job-posts/user")
//public class UserJobPostController {
//
//    private final UserJobPostService userJobPostService;
//
//    public UserJobPostController(UserJobPostService userJobPostService) {
//        this.userJobPostService = userJobPostService;
//    }
//
//    @GetMapping
//    public ResponseEntity<DataResult<Collection<UserJobPost>>> getAll() {
//        var result = userJobPostService.getAll();
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/{userId}")
//    public ResponseEntity<DataResult<UserJobPost>> create(Authentication authentication,
//                                                          @PathVariable Long userId,
//                                                          @Valid @RequestPart("userJobPostDto")
//                                                                      UserJobPostDto userJobPostDto,
//                                                          @RequestPart(value = "file", required = false)
//                                                                      MultipartFile multipartFile
//    ) {
//        var result =
//                userJobPostService.create(authentication, userId, userJobPostDto, multipartFile);
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}/{jobPostId}")
//    public ResponseEntity<Result> delete(Authentication authentication,
//                                         @PathVariable Long userId,
//                                         @PathVariable Long jobPostId) {
//        var result = userJobPostService.delete(authentication, userId, jobPostId);
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//
//    }
//}