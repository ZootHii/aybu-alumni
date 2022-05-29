package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    @Query("select cp from CommunityPost cp where cp.ownerCommunityPage.id = :communityPageId order by cp.id desc")
    Page<CommunityPost> getTopsByOwnerCommunityPage(@Param("communityPageId") Long communityPageId, Pageable pageable);
}
