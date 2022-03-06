package com.aybu9.aybualumni.user.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.utilities.storage.FileStorage;
import com.aybu9.aybualumni.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserManagerTest {

    private UserRepository userRepository;
    private FileStorage fileStorage;
    private AuthService authService;
    private UserContactInfoService userContactInfoService;

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        fileStorage = mock(FileStorage.class);
        authService = mock(AuthService.class);
        userContactInfoService = mock(UserContactInfoService.class);

        userManager = new UserManager(userRepository, fileStorage, authService, userContactInfoService);
    }

    @Test
    void testGetAll_itShouldReturnSuccessDataUsers() {
        when(userManager.getAll());
    }


}