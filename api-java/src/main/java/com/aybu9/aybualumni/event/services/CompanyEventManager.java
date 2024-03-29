package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.event.models.CompanyEvent;
import com.aybu9.aybualumni.event.models.Event;
import com.aybu9.aybualumni.event.models.dtos.CompanyEventDto;
import com.aybu9.aybualumni.event.repositories.CompanyEventRepository;
import com.aybu9.aybualumni.page.services.CityService;
import com.aybu9.aybualumni.user.services.UserService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashSet;

import static com.aybu9.aybualumni.core.models.Constants.VISIBILITY_EVERYONE;

@Service
public class CompanyEventManager implements CompanyEventService {

    private final CompanyEventRepository companyEventRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final UserService userService;
    private final EventService eventService;
    private final FileStorage fileStorage;

    public CompanyEventManager(CompanyEventRepository companyEventRepository, AuthService authService,
                               CityService cityService, UserService userService, EventService eventService,
                               FileStorage fileStorage) {
        this.companyEventRepository = companyEventRepository;
        this.authService = authService;
        this.cityService = cityService;
        this.userService = userService;
        this.eventService = eventService;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CompanyEvent>> getAll() {
        return new SuccessDataResult<>(companyEventRepository.findAll(), "get all success");
    }

    @Override
    public DataResult<Collection<CompanyEvent>> getAllPageable(Pageable pageable) {
        return new SuccessDataResult<>(companyEventRepository.findAll(pageable).getContent(),
                "get all pageable success");
    }

    @Override
    @Transactional
    public DataResult<CompanyEvent> create(Authentication authentication, Long userId,
                                           CompanyEventDto companyEventDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCompanyPage = currentUser.getCompanyPage();

        if (currentUserCompanyPage == null) {
            throw new CustomException("current user has no company");
        }

        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
        var name = companyEventDto.getName();
        var description = companyEventDto.getDescription();
        var isOnline = companyEventDto.getIsOnline();
        var cityId = companyEventDto.getCityId();
        var address = companyEventDto.getAddress();
        var startDateTime = companyEventDto.getStartDateTime();
        var endDateTime = companyEventDto.getEndDateTime();
        var visibility = companyEventDto.getVisibility();
        var eventSpeakerUsersIds = companyEventDto.getEventSpeakerUsersIds();

        var event = new Event(name, currentUser, isOnline, address, startDateTime, endDateTime);

        if (Strings.isNullOrEmpty(visibility)) {
            visibility = VISIBILITY_EVERYONE;
        }

        if (!Strings.isNullOrEmpty(fileUrl)) {
            event.setFileUrl(fileUrl);
        }

        if (!isOnline && cityId == null) {
            throw new CustomException("City must be selected in face to face events");
        }

        if (cityId != null && !isOnline) {
            var city = cityService.get(cityId).getData();
            event.setCity(city);
        }

        if (!Strings.isNullOrEmpty(description)) {
            event.setDescription(description);
        }

        if (eventSpeakerUsersIds != null && !eventSpeakerUsersIds.isEmpty()) {
            var eventSpeakerUsers = new HashSet<>(userService.getAllByIdIn(eventSpeakerUsersIds).getData());
            event.setEventSpeakerUsers(eventSpeakerUsers);
        }

        var createdEvent = eventService.create(event).getData();

        var companyEvent = new CompanyEvent(createdEvent, currentUserCompanyPage, visibility);

        var createdCompanyEvent = companyEventRepository.save(companyEvent);
        return new SuccessDataResult<>(createdCompanyEvent, "company event created success");
    }
}