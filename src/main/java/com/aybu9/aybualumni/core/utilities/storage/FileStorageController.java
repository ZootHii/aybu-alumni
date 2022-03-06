package com.aybu9.aybualumni.core.utilities.storage;

import com.aybu9.aybualumni.auth.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/core/file-storage")
public class FileStorageController {

    private final FileStorage fileStorage;
    private final AuthService authService;

    public FileStorageController(FileStorage fileStorage, AuthService authService) {
        this.fileStorage = fileStorage;
        this.authService = authService;
    }

    @PostMapping("/save-file/{userId}")
    public ResponseEntity<String> saveFile(Authentication authentication,
                                           @PathVariable Long userId,
                                           @RequestParam("file") MultipartFile multipartFile
    ) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var result = fileStorage.saveFile(currentUser, multipartFile);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
