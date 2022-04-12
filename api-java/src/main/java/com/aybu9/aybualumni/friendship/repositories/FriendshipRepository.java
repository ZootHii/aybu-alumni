package com.aybu9.aybualumni.friendship.repositories;

import com.aybu9.aybualumni.friendship.models.Friendship;
import com.aybu9.aybualumni.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    
    // friends of user ----
    @Query("SELECT U " +
            "From User AS U " +
            "WHERE U.id IN (" +
                            "SELECT (CASE WHEN F.sender.id = ?1 THEN F.receiver.id ELSE F.sender.id END) " +
                            "FROM Friendship AS F " +
                            "WHERE ((F.sender.id = ?1 OR F.receiver.id = ?1) AND F.isAccepted = true)) " +
            "ORDER BY U.id")
    Collection<User> getFriendshipsByUserId(Long userId);

    // kullanıcıya gelen ve bekleyen istekler ----
    @Query("SELECT F1 " +
            "From Friendship AS F1 " +
            "WHERE F1.id IN (" +
            "SELECT (CASE WHEN F.receiver.id= ?1 THEN F.id ELSE 0 END) " +
            "FROM Friendship AS F " +
            "WHERE ((F.sender.id = ?1 OR F.receiver.id = ?1) AND F.isAccepted = false)) " +
            "ORDER BY F1.id")
    Collection<Friendship> getFriendshipRequestsIncomingByUserId(Long userId);
    
    // kullanıcın gönderdiği bekleyen istekler ----
    @Query("SELECT F1 " +
            "From Friendship AS F1 " +
            "WHERE F1.id IN (" +
            "SELECT (CASE WHEN F.sender.id= ?1 THEN F.id ELSE 0 END) " +
            "FROM Friendship AS F " +
            "WHERE ((F.sender.id = ?1 OR F.receiver.id = ?1) AND F.isAccepted = false)) " +
            "ORDER BY F1.id")
    Collection<Friendship> getFriendshipRequestsOutgoingByUserId(Long userId);

    Friendship getBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
    Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Friendship F SET F.isAccepted = TRUE WHERE F.id = ?1")
    void acceptFriendshipRequest(Long friendshipId);
    
    // todo ortada istek kabul edilmemiş veya edilmiş istek var mı arada friendship var mı onu kontrol et ona göre buton ayarla
    /* 1 4 e istek atmış kabul edilmemiş ve 4 1 e istek atmaya çalışıyor bu kez kabul et butonu çıkar */
    /* 1 4 e istek atmış kabul edilmiş ve 4 1 e istek atmaya çalışıyor bu kez buton gösterme */
    
    // how to make uneditable file in java spring 

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
// , users.password, users.name, users.surname, users.about, users.profile_url, users.profile_photo_url, users.cover_photo_url, users.contact_info_id, users.company_page_id, users.community_page_id, users.created_at, users.updated_at
//    @Query(value = "SELECT * /*NEW com.aybu9.aybualumni.user.models.User(users.id, users.email)*/ FROM users " +
//            "WHERE users.id IN (SELECT (CASE WHEN user_id=?1 THEN friend_id ELSE user_id END) AS friend_id FROM friendships WHERE (user_id=?1 OR friend_id=?1) AND is_ac´´cepted = true) " +
//            "ORDER BY users.id ASC;", nativeQuery = true)
//    Collection<Object> getFriendsOfUserByUserId(Long userId);


//    Query query = em.createNativeQuery("SELECT * FROM users \n" +
//            "WHERE users.id IN (SELECT (CASE WHEN user_id=?1 THEN friend_id ELSE user_id END) AS friend_id FROM friendships WHERE (user_id=?1 OR friend_id=?1) AND is_accepted = true)\n" +
//            "ORDER BY users.id ASC;", "friendsResult");
    //@SuppressWarnings("unchecked")
//Collection<User> friends = query.getResultList();


//    Collection<User> getFriends(Long userId);
}
