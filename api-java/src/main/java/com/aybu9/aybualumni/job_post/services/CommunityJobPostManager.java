package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.job_post.models.CommunityJobPost;
import com.aybu9.aybualumni.job_post.models.JobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CommunityJobPostDto;
import com.aybu9.aybualumni.job_post.repositories.CommunityJobPostRepository;
import com.aybu9.aybualumni.job_post.repositories.JobSkillRepository;
import com.aybu9.aybualumni.job_post.repositories.JobTitleRepository;
import com.aybu9.aybualumni.page.services.CityService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CommunityJobPostManager implements CommunityJobPostService {

    private final CommunityJobPostRepository communityJobPostRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final JobPostService jobPostService;
    private final JobTitleRepository jobTitleRepository;
    private final JobSkillRepository jobSkillRepository;
    private final FileStorage fileStorage;

    public CommunityJobPostManager(CommunityJobPostRepository communityJobPostRepository, AuthService authService,
                                   CityService cityService, JobPostService jobPostService,
                                   JobTitleRepository jobTitleRepository, JobSkillRepository jobSkillRepository,
                                   FileStorage fileStorage) {
        this.communityJobPostRepository = communityJobPostRepository;
        this.authService = authService;
        this.cityService = cityService;
        this.jobPostService = jobPostService;
        this.jobTitleRepository = jobTitleRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CommunityJobPost>> getAll() {
        return new SuccessDataResult<>(communityJobPostRepository.findAll(), "get all success");
    }

    @Override
    public DataResult<Collection<CommunityJobPost>> getAllPageable(Pageable pageable) {
        return new SuccessDataResult<>(communityJobPostRepository.findAll(pageable).getContent(),
                "get all pageable success");
    }

    @Override
    @Transactional
    public DataResult<CommunityJobPost> create(Authentication authentication, Long userId,
                                               CommunityJobPostDto communityJobPostDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCommunityPage = currentUser.getCommunityPage();

        if (currentUserCommunityPage == null) {
            throw new CustomException("current user has no community");
        }

        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
        var description = communityJobPostDto.getDescription();
        var jobTitleId = communityJobPostDto.getJobTitleId();
        var jobSkillsIds = communityJobPostDto.getJobSkillsIds();
        var cityId = communityJobPostDto.getCityId();
        var workplaceType = communityJobPostDto.getWorkplaceType();
        var jobType = communityJobPostDto.getJobType();

        var jobPost = new JobPost(currentUser, workplaceType, jobType);

        var jobTitle = jobTitleRepository.findById(jobTitleId)
                .orElseThrow(() -> new CustomException("not found job title"));
        jobPost.setJobTitle(jobTitle);

        if (!Strings.isNullOrEmpty(fileUrl)) {
            jobPost.setFileUrl(fileUrl);
        }

        if (cityId != null) {
            var city = cityService.get(cityId).getData();
            jobPost.setCity(city);
        }

        if (!Strings.isNullOrEmpty(description)) {
            jobPost.setDescription(description);
        }

        if (jobSkillsIds != null && !jobSkillsIds.isEmpty()) {
            var eventSpeakerUsers = new HashSet<>(jobSkillRepository.getAllByIdIn(jobSkillsIds));
            jobPost.setJobSkills(eventSpeakerUsers);
        }

        var createdJobPost = jobPostService.create(jobPost).getData();

        var communityJobPost = new CommunityJobPost(createdJobPost, currentUserCommunityPage);

        var createdCommunityJobPost = communityJobPostRepository.save(communityJobPost);
        return new SuccessDataResult<>(createdCommunityJobPost, "community job post created success");
    }

    @Override
    @Transactional
    public Result delete(Authentication authentication, Long userId, Long jobPostId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return jobPostService.delete(jobPostId);
    }
}
