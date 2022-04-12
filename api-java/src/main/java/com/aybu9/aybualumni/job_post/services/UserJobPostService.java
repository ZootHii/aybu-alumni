//package com.aybu9.aybualumni.job_post.services;
//
//import com.aybu9.aybualumni.core.result.DataResult;
//import com.aybu9.aybualumni.core.result.Result;
//import com.aybu9.aybualumni.job_post.models.UserJobPost;
//import com.aybu9.aybualumni.job_post.models.dtos.UserJobPostDto;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Collection;
//
//public interface UserJobPostService {
//
//    DataResult<Collection<UserJobPost>> getAll();
//
//    DataResult<UserJobPost> create(Authentication authentication, Long userId, UserJobPostDto userJobPostDto,
//                                        MultipartFile multipartFile);
//    Result delete(Authentication authentication, Long userId, Long jobPostId);
//}
