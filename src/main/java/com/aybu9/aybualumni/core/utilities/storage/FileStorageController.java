package com.aybu9.aybualumni.core.utilities.storage;

import com.aybu9.aybualumni.auth.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/core/file-storage")
@CrossOrigin
public class FileStorageController {

    private final FileStorage fileStorage;
    private final AuthService authService;

    public FileStorageController(FileStorage fileStorage, AuthService authService) {
        this.fileStorage = fileStorage;
        this.authService = authService;
    }

    @PostMapping("/save-file/{userId}/{folderName}")
    public ResponseEntity<String> saveFile(Authentication authentication,
                                           @PathVariable Long userId,
                                           @RequestParam("file") MultipartFile multipartFile,
                                           @PathVariable String folderName
    ) {
        var currentUser = authService.getCurrentUserAccessible(authentication, userId);
        var result = fileStorage.saveFile(currentUser, multipartFile, folderName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
