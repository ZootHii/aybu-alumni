package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.event.models.CommunityEvent;
import com.aybu9.aybualumni.event.models.Event;
import com.aybu9.aybualumni.event.models.dtos.CommunityEventDto;
import com.aybu9.aybualumni.event.repositories.CommunityEventRepository;
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
public class CommunityEventManager implements CommunityEventService {

    private final CommunityEventRepository communityEventRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final UserService userService;
    private final EventService eventService;
    private final FileStorage fileStorage;

    public CommunityEventManager(CommunityEventRepository communityEventRepository, AuthService authService,
                                 CityService cityService, UserService userService, EventService eventService,
                                 FileStorage fileStorage) {
        this.communityEventRepository = communityEventRepository;
        this.authService = authService;
        this.cityService = cityService;
        this.userService = userService;
        this.eventService = eventService;
        this.fileStorage = fileStorage;
    }

    @Override
    public DataResult<Collection<CommunityEvent>> getAll() {
        return new SuccessDataResult<>(communityEventRepository.findAll(), "get all success");
    }

    @Override
    public DataResult<Collection<CommunityEvent>> getAllPageable(Pageable pageable) {
        return new SuccessDataResult<>(communityEventRepository.findAll(pageable).getContent(),
                "get all pageable success");
    }

    @Override
    @Transactional
    public DataResult<CommunityEvent> create(Authentication authentication, Long userId,
                                             CommunityEventDto communityEventDto, MultipartFile multipartFile) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCommunityPage = currentUser.getCommunityPage();

        if (currentUserCommunityPage == null) {
            throw new CustomException("current user has no community");
        }

        var fileUrl = fileStorage.saveFile(currentUser, multipartFile);
        var name = communityEventDto.getName();
        var description = communityEventDto.getDescription();
        var isOnline = communityEventDto.getIsOnline();
        var cityId = communityEventDto.getCityId();
        var address = communityEventDto.getAddress();
        var startDateTime = communityEventDto.getStartDateTime();
        var endDateTime = communityEventDto.getEndDateTime();
        var visibility = communityEventDto.getVisibility();
        var eventSpeakerUsersIds = communityEventDto.getEventSpeakerUsersIds();

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

        var communityEvent = new CommunityEvent(createdEvent, currentUserCommunityPage, visibility);

        var createdCommunityEvent = communityEventRepository.save(communityEvent);
        return new SuccessDataResult<>(createdCommunityEvent, "community event created success");
    }
}