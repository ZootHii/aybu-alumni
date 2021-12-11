package com.aybu9.aybualumni.friendship.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.friendship.repositories.FriendshipRepository;
import com.aybu9.aybualumni.user.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class FriendshipManager implements FriendshipService {
    
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    
    public FriendshipManager(FriendshipRepository friendshipRepository, UserService userService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
    }

    @Override
    public DataResult<Friendship> getById(Long friendshipId) {
        var friendship = friendshipRepository.findById(friendshipId);
        if (friendship.isEmpty()) {
            throw new CustomException("Friendship not found by id");
        }
        return new SuccessDataResult<>(friendship.get(), "found by id");
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
    public DataResult<Friendship> sendFriendshipRequest(Long senderId, Long receiverId) { // sender, receiver
        var user = userService.get(senderId).getData();
        var friend = userService.get(receiverId).getData();
        // check if there is already a request
        var existsBySenderIdAndReceiverId = existsBySenderIdAndReceiverId(senderId, receiverId);
        if (existsBySenderIdAndReceiverId){
            throw new CustomException("there is already a friendship");
        }
        var friendship = new Friendship(user, friend, false);
        var createdFriendship = friendshipRepository.save(friendship);
        return new SuccessDataResult<>(createdFriendship, "sendFriendshipRequest");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DataResult<Friendship> acceptFriendshipRequest(Long receiverId, Long senderId) { // receiver, sender -> receiver accept eden
        userService.get(receiverId);
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
    public Result deleteFriendshipRequest(Long receiverId, Long senderId) {
        userService.get(receiverId);
        userService.get(senderId);
        Friendship friendship;
        // check if there is a friendship
        // get friendship by userId and friendId
        if (existsBySenderIdAndReceiverId(senderId, receiverId)){
            friendship = getBySenderIdAndReceiverId(senderId, receiverId).getData();
        } else if (existsBySenderIdAndReceiverId(receiverId, senderId)){
            friendship = getBySenderIdAndReceiverId(receiverId, senderId).getData();
        }
        else {
            throw new CustomException("there is no friendship");
        }
        friendshipRepository.deleteById(friendship.getId());
        return new SuccessResult("deleteFriendshipRequest");
    }

    @Override
    public DataResult<Collection<User>> getFriendshipsByUserId(Long userId){
    // check user exisst
        userService.get(userId);
//        var objectMapper = new ObjectMapper();
//        Query query = entityManager.createNativeQuery(
//                "SELECT users.id, users.email, users.password, users.name, users.surname FROM users \n" +
//                        "WHERE users.id IN (SELECT (CASE WHEN user_id=1 THEN friend_id ELSE user_id END) AS friend_id FROM friendships WHERE (user_id=1 OR friend_id=1) AND is_accepted = true)\n" +
//                        "ORDER BY users.id ASC;",
//                "Mapping.UserDto");
//        var result = query.getResultList();
        return new SuccessDataResult<>(friendshipRepository.getFriendshipsByUserId(userId), "get friends");
    }

    @Override
    public DataResult<Collection<Friendship>> getFriendshipRequestsIncomingByUserId(Long userId){
        // check user exissst
        userService.get(userId);
        return new SuccessDataResult<>(friendshipRepository.getFriendshipRequestsIncomingByUserId(userId), "get received pending friendship requests");
    }

    @Override
    public DataResult<Collection<Friendship>> getFriendshipRequestsOutgoingByUserId(Long userId){
        // check user exisst
        userService.get(userId);
        return new SuccessDataResult<>(friendshipRepository.getFriendshipRequestsOutgoingByUserId(userId), "get pending friendship requests ");
    }
    
}
