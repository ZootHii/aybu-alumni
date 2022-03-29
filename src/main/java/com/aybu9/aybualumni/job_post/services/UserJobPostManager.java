//package com.aybu9.aybualumni.job_post.services;
//
//import com.aybu9.aybualumni.auth.services.AuthService;
//import com.aybu9.aybualumni.core.exception.CustomException;
//import com.aybu9.aybualumni.core.result.DataResult;
//import com.aybu9.aybualumni.core.result.Result;
//import com.aybu9.aybualumni.core.result.SuccessDataResult;
//import com.aybu9.aybualumni.core.result.SuccessResult;
//import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
//import com.aybu9.aybualumni.job_post.models.CommunityJobPost;
//import com.aybu9.aybualumni.job_post.models.JobPost;
//import com.aybu9.aybualumni.job_post.models.UserJobPost;
//import com.aybu9.aybualumni.job_post.models.dtos.CommunityJobPostDto;
//import com.aybu9.aybualumni.job_post.models.dtos.UserJobPostDto;
//import com.aybu9.aybualumni.job_post.repositories.CommunityJobPostRepository;
//import com.aybu9.aybualumni.job_post.repositories.JobSkillRepository;
//import com.aybu9.aybualumni.job_post.repositories.JobTitleRepository;
//import com.aybu9.aybualumni.job_post.repositories.UserJobPostRepository;
//import com.aybu9.aybualumni.page.services.CityService;
//import com.google.common.base.Strings;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Collection;
//import java.util.HashSet;
//
//import static com.aybu9.aybualumni.core.models.Constants.VISIBILITY_EVERYONE;
//import static com.aybu9.aybualumni.core.result.Constants.DeleteSuccess;
//
//@Service
//public class UserJobPostManager implements UserJobPostService {
//
//    private final UserJobPostRepository userJobPostRepository;
//    private final AuthService authService;
//    private final CityService cityService;
//    private final JobPostService jobPostService;
//    private final JobTitleRepository jobTitleRepository;
//    private final JobSkillRepository jobSkillRepository;
//    private final FileStorage fileStorage;
//
//    public UserJobPostManager(UserJobPostRepository userJobPostRepository, AuthService authService,
//                                   CityService cityService, JobPostService jobPostService,
//                                   JobTitleRepository jobTitleRepository, JobSkillRepository jobSkillRepository,
//                                   FileStorage fileStorage) {
//        this.userJobPostRepository = userJobPostRepository;
//        this.authService = authService;
//        this.cityService = cityService;
//        this.jobPostService = jobPostService;
//        this.jobTitleRepository = jobTitleRepository;
//        this.jobSkillRepository = jobSkillRepository;
//        this.fileStorage = fileStorage;
//    }
//
//    @Override
//    public DataResult<Collection<UserJobPost>> getAll() {
//        return new SuccessDataResult<>(userJobPostRepository.findAll(), "get all success");
//    }
//
//    @Override
//    @Transactional
//    public DataResult<UserJobPost> create(Authentication authentication, Long userId,
//                                          UserJobPostDto userJobPostDto, MultipartFile multipartFile) {
//        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
//       var currentUserUserPage = currentUser.getUserPage();
//
//        if (currentUserUserPage == null) {
//            throw new CustomException("current user has no community");
//        }
//
//        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
//        var description = userJobPostDto.getDescription();
//        var jobTitleId = userJobPostDto.getJobTitleId();
//        var jobSkillsIds = userJobPostDto.getJobSkillsIds();
//        var cityId = userJobPostDto.getCityId();
//        var workplaceType = userJobPostDto.getWorkplaceType();
//        var jobType = userJobPostDto.getJobType();
//
//        var jobPost = new JobPost(currentUser, workplaceType, jobType);
//
////        if (Strings.isNullOrEmpty(visibility)) {
////           visibility = VISIBILITY_EVERYONE;
////        }
//
//        var jobTitle = jobTitleRepository.findById(jobTitleId)
//                .orElseThrow(() -> new CustomException("not found job title"));
//        jobPost.setJobTitle(jobTitle);
//
//        if (!Strings.isNullOrEmpty(fileUrl)) {
//            jobPost.setFileUrl(fileUrl);
//        }
//
////        if (!isOnline && cityId == null) {
////            throw new CustomException("City must be selected in face to face events");
////       }
//
//        if (cityId != null) {
//            var city = cityService.get(cityId).getData();
//            jobPost.setCity(city);
//        }
//
//        if (!Strings.isNullOrEmpty(description)) {
//            jobPost.setDescription(description);
//        }
//
//        if (jobSkillsIds != null && !jobSkillsIds.isEmpty()) {
//            var eventSpeakerUsers = new HashSet<>(jobSkillRepository.getAllByIdIn(jobSkillsIds));
//            jobPost.setJobSkills(eventSpeakerUsers);
//        }
//
//        var createdJobPost = jobPostService.create(jobPost).getData();
//
//        var userJobPost = new UserJobPost(createdJobPost, currentUserUserPage);
//
//        var createdUserJobPost = userJobPostRepository.save(userJobPost);
//        return new SuccessDataResult<>(createdUserJobPost, "user job post created success");
//    }
//    @Override
//    @Transactional
//    public Result delete(Authentication authentication, Long userId, Long jobPostId) {
//        authService.getCurrentUserAccessible(authentication, userId);
//        return jobPostService.delete(jobPostId);
//    }
//}
