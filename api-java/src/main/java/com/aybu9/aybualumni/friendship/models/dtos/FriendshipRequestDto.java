package com.aybu9.aybualumni.friendship.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequestDto {
    
    @NotNull
    private Long senderId;
    
    @NotNull
    private Long receiverId;
}
