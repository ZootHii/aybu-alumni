package com.aybu9.aybualumni.friendship.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.user.models.User;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface FriendshipService {

    DataResult<Friendship> getById(Long friendshipId);

    Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    DataResult<Friendship> getBySenderIdAndReceiverId(Long senderId, Long receiverId);

    DataResult<Friendship> sendFriendshipRequest(Authentication authentication, Long senderId, Long receiverId);

    DataResult<Friendship> acceptFriendshipRequest(Authentication authentication, Long receiverId, Long senderId);

    Result deleteFriendshipRequest(Authentication authentication, Long receiverId, Long senderId);

    DataResult<Collection<User>> getFriendshipsByUserId(Authentication authentication, Long userId);

    DataResult<Long> getFriendsCountByUserId(Authentication authentication, Long userId);

    DataResult<Collection<Friendship>> getFriendshipRequestsIncomingByUserId(Authentication authentication, Long userId);

    DataResult<Collection<Friendship>> getFriendshipRequestsOutgoingByUserId(Authentication authentication, Long userId);
}
