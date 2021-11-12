package com.aybu9.aybualumni.auth.models.dtos;

import com.aybu9.aybualumni.core.security.token.AccessToken;
import com.aybu9.aybualumni.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
	private AccessToken accessToken;
	private User user;
}
