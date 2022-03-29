package com.aybu9.aybualumni.event.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.event.models.CommunityEvent;
import com.aybu9.aybualumni.event.models.dtos.CommunityEventDto;
import com.aybu9.aybualumni.event.services.CommunityEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/events/community")
public class CommunityEventController {

    private final CommunityEventService communityEventService;

    public CommunityEventController(CommunityEventService communityEventService) {
        this.communityEventService = communityEventService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CommunityEvent>>> getAll() {
        var result = communityEventService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CommunityEvent>> create(Authentication authentication,
                                                             @PathVariable Long userId,
                                                             @Valid @RequestPart("communityEventDto")
                                                                     CommunityEventDto communityEventDto,
                                                             @RequestPart(value = "file", required = false)
                                                                     MultipartFile multipartFile
    ) {
        var result =
                communityEventService.create(authentication, userId, communityEventDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}