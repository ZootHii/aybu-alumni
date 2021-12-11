package com.aybu9.aybualumni.friendship.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.user.models.User;

import java.util.Collection;

public interface FriendshipService {

    DataResult<Friendship> getById(Long friendshipId);
    
    Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    DataResult<Friendship> getBySenderIdAndReceiverId(Long senderId, Long receiverId);

    DataResult<Friendship> sendFriendshipRequest(Long senderId, Long receiverId);

    DataResult<Friendship> acceptFriendshipRequest(Long receiverId, Long senderId);
    
    Result deleteFriendshipRequest(Long receiverId, Long senderId);
    
    DataResult<Collection<User>> getFriendshipsByUserId(Long userId);

    DataResult<Collection<Friendship>> getFriendshipRequestsIncomingByUserId(Long userId);

    DataResult<Collection<Friendship>> getFriendshipRequestsOutgoingByUserId(Long userId);

}
