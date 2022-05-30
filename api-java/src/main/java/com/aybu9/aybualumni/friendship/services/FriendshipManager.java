package com.aybu9.aybualumni.friendship.services;

import com.aybu9.aybualumni.auth.services.AuthService;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.friendship.repositories.FriendshipRepository;
import com.aybu9.aybualumni.user.services.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class FriendshipManager implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final AuthService authService;

    public FriendshipManager(FriendshipRepository friendshipRepository, UserService userService,
                             AuthService authService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public DataResult<Friendship> getById(Long friendshipId) {
        var friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new CustomException("Friendship not found by id"));
        return new SuccessDataResult<>(friendship, "found by id");
    }

    @Override
    public Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return friendshipRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public DataResult<Friendship> getBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        userService.get(senderId);
        userService.get(receiverId);
        var friendship = friendshipRepository.getBySenderIdAndReceiverId(senderId, receiverId);
        if (friendship == null) {
            throw new CustomException("Friendship not found by ids");
        }
        return new SuccessDataResult<>(friendship, "found by ids");
    }

    @Override
    @Transactional
    public DataResult<Friendship> sendFriendshipRequest(Authentication authentication, Long senderId, Long receiverId) {
        var user = authService.getCurrentUserAccessible(authentication, senderId);
        var friend = userService.get(receiverId).getData();
        // check if there is already a request
        var existsBySenderIdAndReceiverId = existsBySenderIdAndReceiverId(senderId, receiverId);
        if (existsBySenderIdAndReceiverId) {
            throw new CustomException("there is already a friendship");
        }
        var friendship = new Friendship(user, friend, false);
        var createdFriendship = friendshipRepository.save(friendship);
        return new SuccessDataResult<>(createdFriendship, "sendFriendshipRequest");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DataResult<Friendship> acceptFriendshipRequest(Authentication authentication, Long receiverId, Long senderId) {
        authService.getCurrentUserAccessible(authentication, receiverId);
        userService.get(senderId);
        // check if there is a friendship
        // get friendship by userId and friendId
        var friendship = getBySenderIdAndReceiverId(senderId, receiverId).getData();
        // update friendship
        friendshipRepository.acceptFriendshipRequest(friendship.getId());
        var updatedFriendship = getById(friendship.getId()).getData();
        return new SuccessDataResult<>(updatedFriendship, "acceptFriendshipRequest");
    }

    @Override
    @Transactional
    public Result deleteFriendshipRequest(Authentication authentication, Long receiverId, Long senderId) {
        userService.get(receiverId);
        userService.get(senderId);
        Friendship friendship;
        // check if there is a friendship
        // get friendship by userId and friendId
        if (existsBySenderIdAndReceiverId(senderId, receiverId)) {
            authService.getCurrentUserAccessible(authentication, senderId);
            friendship = getBySenderIdAndReceiverId(senderId, receiverId).getData();
        } else if (existsBySenderIdAndReceiverId(receiverId, senderId)) {
            authService.getCurrentUserAccessible(authentication, receiverId);
            friendship = getBySenderIdAndReceiverId(receiverId, senderId).getData();
        } else {
            throw new CustomException("there is no friendship");
        }
        friendshipRepository.deleteById(friendship.getId());
        return new SuccessResult("deleteFriendshipRequest");
    }

    @Override
    public DataResult<Collection<User>> getFriendshipsByUserId(Authentication authentication, Long userId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return new SuccessDataResult<>(friendshipRepository.getFriendshipsByUserId(userId), "get friends");
    }

    @Override
    public DataResult<Collection<User>> getFriendshipsByUserIdPageable(Authentication authentication,
                                                                       Long userId, Pageable pageable) {
        authService.getCurrentUserAccessible(authentication, userId);
        var friendships = friendshipRepository.getFriendshipsByUserIdPageable(userId, pageable).getContent();
        return new SuccessDataResult<>(friendships, "get friends pageable");
    }

    @Override
    public DataResult<Long> getFriendsCountByUserId(Authentication authentication, Long userId) {
        var friendships = getFriendshipsByUserId(authentication, userId).getData();
        var friendsCount = (long) friendships.size();
        return new SuccessDataResult<>(friendsCount, "get friends count");
    }

    @Override
    public DataResult<Collection<Friendship>> getFriendshipRequestsIncomingByUserId(Authentication authentication,
                                                                                    Long userId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return new SuccessDataResult<>(friendshipRepository.getFriendshipRequestsIncomingByUserId(userId),
                "get received pending friendship requests");
    }

    @Override
    public DataResult<Collection<Friendship>> getFriendshipRequestsOutgoingByUserId(Authentication authentication,
                                                                                    Long userId) {
        authService.getCurrentUserAccessible(authentication, userId);
        return new SuccessDataResult<>(friendshipRepository.getFriendshipRequestsOutgoingByUserId(userId),
                "get pending friendship requests ");
    }

}
