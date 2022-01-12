package com.aybu9.aybualumni.event.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.event.models.CompanyEvent;
import com.aybu9.aybualumni.event.models.dtos.CompanyEventDto;
import com.aybu9.aybualumni.event.services.CompanyEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/events/company")
@CrossOrigin
public class CompanyEventController {

    private final CompanyEventService companyEventService;

    public CompanyEventController(CompanyEventService companyEventService) {
        this.companyEventService = companyEventService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<CompanyEvent>>> getAll() {
        var result = companyEventService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DataResult<CompanyEvent>> create(Authentication authentication,
                                                           @PathVariable Long userId,
                                                           @Valid @RequestPart("companyEventDto")
                                                                   CompanyEventDto companyEventDto,
                                                           @RequestPart(value = "file", required = false)
                                                                   MultipartFile multipartFile
    ) {
        var result =
                companyEventService.create(authentication, userId, companyEventDto, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
