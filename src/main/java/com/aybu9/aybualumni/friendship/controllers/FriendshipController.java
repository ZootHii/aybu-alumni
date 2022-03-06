package com.aybu9.aybualumni.friendship.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.friendship.models.dtos.FriendshipRequestDto;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.friendship.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DataResult<Collection<User>>> getFriendsByUserId(@PathVariable Long userId) { // todo auth
        var result = friendshipService.getFriendshipsByUserId(userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/requests/incoming")
    public ResponseEntity<DataResult<Collection<Friendship>>> getFriendshipRequestsIncomingByUserId(
            @PathVariable Long userId) { // todo auth
        var result = friendshipService.getFriendshipRequestsIncomingByUserId(userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/requests/outgoing")
    public ResponseEntity<DataResult<Collection<Friendship>>> getFriendshipRequestsOutgoingByUserId(
            @PathVariable Long userId) { // todo auth
        var result = friendshipService.getFriendshipRequestsOutgoingByUserId(userId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/send-request")
    public ResponseEntity<DataResult<Friendship>> sendFriendshipRequest(
            @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) { // todo auth
        var result = friendshipService.sendFriendshipRequest(friendshipRequestDto.getSenderId(),
                friendshipRequestDto.getReceiverId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/accept-request")
    public ResponseEntity<DataResult<Friendship>> acceptFriendshipRequest(
            @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) { // todo auth
        var result = friendshipService.acceptFriendshipRequest(friendshipRequestDto.getReceiverId(),
                friendshipRequestDto.getSenderId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete-request")
    public ResponseEntity<Result> deleteFriendshipRequest( // todo auth
                                                           @Valid @RequestBody FriendshipRequestDto friendshipRequestDto) {
        var result = friendshipService.deleteFriendshipRequest(friendshipRequestDto.getReceiverId(),
                friendshipRequestDto.getSenderId());
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
