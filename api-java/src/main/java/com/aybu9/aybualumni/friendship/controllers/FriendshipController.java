package com.aybu9.aybualumni.friendship.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.friendship.models.dtos.FriendshipRequestDto;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.friendship.services.FriendshipService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResult<Collection<User>>> getFriendshipsByUserId(Authentication authentication,
                                                                               @PathVariable Long userId) {
        var result = friendshipService.getFriendshipsByUserId(authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/pageable/{page}/{size}")
    public ResponseEntity<DataResult<Collection<User>>> getFriendshipsByUserIdPageable(Authentication authentication,
                                                                                       @PathVariable Long userId,
                                                                                       @PathVariable Integer page,
                                                                                       @PathVariable Integer size) {
        var result =
                friendshipService.getFriendshipsByUserIdPageable(authentication, userId, PageRequest.of(page, size));
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<DataResult<Long>> getFriendsCountByUserId(Authentication authentication,
                                                                    @PathVariable Long userId) {
        var result = friendshipService.getFriendsCountByUserId(authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/requests/incoming")
    public ResponseEntity<DataResult<Collection<Friendship>>> getFriendshipRequestsIncomingByUserId(
            Authentication authentication, @PathVariable Long userId) {
        var result = friendshipService.getFriendshipRequestsIncomingByUserId(
                authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/requests/outgoing")
    public ResponseEntity<DataResult<Collection<Friendship>>> getFriendshipRequestsOutgoingByUserId(
            Authentication authentication, @PathVariable Long userId) {
        var result = friendshipService.getFriendshipRequestsOutgoingByUserId(
                authentication, userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/send-request") // sender id == auth id
    public ResponseEntity<DataResult<Friendship>> sendFriendshipRequest(
            Authentication authentication, @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) {
        var result = friendshipService.sendFriendshipRequest(authentication,
                friendshipRequestDto.getSenderId(),
                friendshipRequestDto.getReceiverId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/accept-request") // receiver id == auth id
    public ResponseEntity<DataResult<Friendship>> acceptFriendshipRequest(
            Authentication authentication, @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) {
        var result = friendshipService.acceptFriendshipRequest(authentication,
                friendshipRequestDto.getReceiverId(),
                friendshipRequestDto.getSenderId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete-request") // receiver id || sender id == auth id
    public ResponseEntity<Result> deleteFriendshipRequest(Authentication authentication,
                                                          @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) {
        var result = friendshipService.deleteFriendshipRequest(
                authentication,
                friendshipRequestDto.getReceiverId(),
                friendshipRequestDto.getSenderId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
