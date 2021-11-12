package com.aybu9.aybualumni.auth.controllers;

import com.aybu9.aybualumni.auth.models.dtos.AuthResponseDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthLoginDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthRegisterDto;
import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auths")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthResponseDto>> login(@Valid @RequestBody AuthLoginDto authLoginDto) {
        var result = authService.login(authLoginDto);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<DataResult<AuthResponseDto>> register(@Valid @RequestBody AuthRegisterDto authRegisterDto) {
        var result = authService.register(authRegisterDto);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Result> logout() {
        var result = authService.logout();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }
}
