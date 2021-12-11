package com.aybu9.aybualumni.user.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import com.aybu9.aybualumni.user.services.UserContactInfoService;
import com.aybu9.aybualumni.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    // TODO: 1.11.2021 CONTROLLER RESPONSE ENTITIY DONECEK YADA DÖNMESEDE OLUR TARTIŞ İSMAİLLE

    private final UserService userService;
    private final UserContactInfoService userContactInfoService;

    @Autowired
    public UserController(UserService userService, UserContactInfoService userContactInfoService) {
        this.userService = userService;
        this.userContactInfoService = userContactInfoService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<User>>> getAll() {
        var result = userService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResult<User>> get(@PathVariable Long userId) {
        var result = userService.get(userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/profile/{uniqueName}")
    public ResponseEntity<DataResult<User>> getByProfileUrl(@PathVariable String uniqueName, HttpServletRequest request) {
        var profileUrl = request.getRequestURL().toString();
        var result = userService.getByProfileUrl(profileUrl);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Result> delete(Authentication authentication, @PathVariable Long userId) {
        var result = userService.delete(authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(
            path = "{userId}/profile-image",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Result> uploadProfileImage(@PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        var result = userService.uploadProfileImage(authentication, userId, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(
            path = "{userId}/cover-image",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Result> uploadCoverImage(Authentication authentication, @PathVariable Long userId,
                                                   @RequestParam("file") MultipartFile multipartFile) {
        var result = userService.uploadCoverImage(authentication, userId, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("{userId}/contact-info")
    public ResponseEntity<Result> updateUserContactInfo(Authentication authentication, @PathVariable Long userId, 
                                                        @Valid @RequestBody UserContactInfoDto userContactInfoDto) {
        var result = userContactInfoService.update(authentication, userId, userContactInfoDto);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping( // todo put post emin değilim
            path = "{userId}/contact-info/resume",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Result> uploadResume(Authentication authentication, @PathVariable Long userId,
                                               @RequestParam("file") MultipartFile multipartFile) {
        var result = userContactInfoService.uploadResume(authentication, userId, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // todo fotoğraflar vesaire download gerek yok belgeler için olabilir url ile gösterme yapılacak picasso gibi
//    @GetMapping(path = "{userId}/download/profile-photo", produces = APPLICATION_JSON_VALUE)
//    public byte[] downloadProfilePhoto(@PathVariable Long userId) {
//        return userService.downloadProfilePhoto(userId).getData();
//    }
}
