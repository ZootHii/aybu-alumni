package com.aybu9.aybualumni.auth.services;

import com.aybu9.aybualumni.auth.models.dtos.AuthResponseDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthLoginDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthRegisterDto;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.User;
import org.springframework.security.core.Authentication;

public interface AuthService {

    DataResult<AuthResponseDto> register(AuthRegisterDto authRegisterDto);

    DataResult<AuthResponseDto> login(AuthLoginDto authLoginDto);

    Result logout();

    User getCurrentUser(Authentication authentication);
}
