package com.aybu9.aybualumni.job_post.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.job_post.models.CompanyJobPost;
import com.aybu9.aybualumni.job_post.models.JobPost;
import com.aybu9.aybualumni.job_post.models.dtos.CompanyJobPostDto;
import com.aybu9.aybualumni.job_post.repositories.CompanyJobPostRepository;
import com.aybu9.aybualumni.job_post.repositories.JobSkillRepository;
import com.aybu9.aybualumni.job_post.repositories.JobTitleRepository;
import com.aybu9.aybualumni.page.services.CityService;
import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CompanyJobPostManager implements CompanyJobPostService {

    private final CompanyJobPostRepository companyJobPostRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final JobPostService jobPostService;
    private final JobTitleRepository jobTitleRepository;
    private final JobSkillRepository jobSkillRepository;
    private final FileStorage fileStorage;

    public CompanyJobPostManager(CompanyJobPostRepository companyJobPostRepository, AuthService authService,
                                 CityService cityService, JobPostService jobPostService,
                                 JobTitleRepository jobTitleRepository, JobSkillRepository jobSkillRepository,
                                 FileStorage fileStorage) {
        this.companyJobPostRepository = companyJobPostRepository;
        this.authService = authService;
        this.cityService = cityService;
        this.jobPostService = jobPostService;
        this.jobTitleRepository = jobTitleRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CompanyJobPost>> getAll() {
        return new SuccessDataResult<>(companyJobPostRepository.findAll(), "get all success");
    }

    @Override
    @Transactional
    public DataResult<CompanyJobPost> create(Authentication authentication, Long userId,
                                             CompanyJobPostDto companyJobPostDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCompanyPage = currentUser.getCompanyPage();

        if (currentUserCompanyPage == null) {
            throw new CustomException("current user has no company");
        }

        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
        var description = companyJobPostDto.getDescription();
        var jobTitleId = companyJobPostDto.getJobTitleId();
        var jobSkillsIds = companyJobPostDto.getJobSkillsIds();
        var cityId = companyJobPostDto.getCityId();
        var workplaceType = companyJobPostDto.getWorkplaceType();
        var jobType = companyJobPostDto.getJobType();

        var jobPost = new JobPost(currentUser, workplaceType, jobType);

//        if (Strings.isNullOrEmpty(visibility)) {
//            visibility = VISIBILITY_EVERYONE;
//        }

        var jobTitle = jobTitleRepository.findById(jobTitleId)
                .orElseThrow(() -> new CustomException("not found job title"));
        jobPost.setJobTitle(jobTitle);

        if (!Strings.isNullOrEmpty(fileUrl)) {
            jobPost.setFileUrl(fileUrl);
        }

//        if (!isOnline && cityId == null) {
//            throw new CustomException("City must be selected in face to face events");
//        }

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

        var companyJobPost = new CompanyJobPost(createdJobPost, currentUserCompanyPage);

        var createdCompanyJobPost = companyJobPostRepository.save(companyJobPost);
        return new SuccessDataResult<>(createdCompanyJobPost, "company job post created success");
    }
}
