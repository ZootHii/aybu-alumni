package com.aybu9.aybualumni.user.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    // TODO: 1.11.2021 CONTROLLER RESPONSE ENTITIY DONECEK YADA DÖNMESEDE OLUR TARTIŞ İSMAİLLE

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<User>>> getAll() {
        var result = userService.getAll();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<DataResult<User>> get(@PathVariable Long userId) {
//        var result = userService.get(userId);
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

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
            path = "{userId}/profile-photo",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Result> updateProfilePhoto(@PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        var result = userService.updateProfilePhoto(authentication, userId, multipartFile);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(
            path = "{userId}/cover-photo",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Result> updateCoverPhoto(Authentication authentication, @PathVariable Long userId,
                                                   @RequestParam("file") MultipartFile multipartFile) {
        var result = userService.updateCoverPhoto(authentication, userId, multipartFile);
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
