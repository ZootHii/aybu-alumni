package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.event.models.CompanyEvent;
import com.aybu9.aybualumni.event.models.Event;
import com.aybu9.aybualumni.event.models.dtos.CompanyEventDto;
import com.aybu9.aybualumni.event.repositories.CompanyEventRepository;
import com.aybu9.aybualumni.page.services.CityService;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.services.UserService;
import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CompanyEventManager implements CompanyEventService {
    
    private final CompanyEventRepository companyEventRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final UserService userService;
    private final EventService eventService;

    public CompanyEventManager(CompanyEventRepository companyEventRepository, AuthService authService, CityService cityService, UserService userService, EventService eventService) {
        this.companyEventRepository = companyEventRepository;
        this.authService = authService;
        this.cityService = cityService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public DataResult<Collection<CompanyEvent>> getAll() {
        return new SuccessDataResult<>(companyEventRepository.findAll(), "get all success");
    }

    @Override
    @Transactional
    public DataResult<CompanyEvent> create(Authentication authentication, Long userId, 
                                           CompanyEventDto companyEventDto) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var currentUserCompanyPage = currentUser.getCompanyPage();
        
        var city = cityService.get(58).getData();
        
        var name = companyEventDto.getName();
        var description = companyEventDto.getDescription();
        var fileUrl = companyEventDto.getFileUrl();
        var isOnline = companyEventDto.getIsOnline();
        var cityId = companyEventDto.getCityId();
        var address = companyEventDto.getAddress();
        var startDateTime = companyEventDto.getStartDateTime();
        var endDateTime = companyEventDto.getEndDateTime();
        var visibility = companyEventDto.getVisibility();
        var eventSpeakerUsersIds = companyEventDto.getEventSpeakerUsersIds();
        var eventSpeakerUsers = new HashSet<User>();

        if (Strings.isNullOrEmpty(fileUrl)){
            fileUrl = null;
        }
        
        if (!isOnline && cityId != null){
            city = cityService.get(cityId).getData();
        }
        
        if (eventSpeakerUsersIds != null && !eventSpeakerUsersIds.isEmpty()){
            eventSpeakerUsers = new HashSet<>(userService.getAllByIdIn(eventSpeakerUsersIds).getData());
        }


        var event = new Event(name, currentUser, fileUrl, description, isOnline, city, 
                address, startDateTime, endDateTime, eventSpeakerUsers);
        
        var createdEvent = eventService.create(event).getData();
        
        if (currentUserCompanyPage == null){
            throw new CustomException("current user has no company");
        }
        
        var companyEvent = new CompanyEvent(createdEvent, currentUserCompanyPage, visibility);
        
        var createdCompanyEvent = companyEventRepository.save(companyEvent);
        return new SuccessDataResult<>(createdCompanyEvent, "company event created success");
    }
}
