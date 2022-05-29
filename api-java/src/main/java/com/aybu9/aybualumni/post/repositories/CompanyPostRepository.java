package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.CompanyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPostRepository extends JpaRepository<CompanyPost, Long> {

    @Query("select cp from CompanyPost cp where cp.ownerCompanyPage.id = :companyPageId order by cp.id desc")
    Page<CompanyPost> getTopsByOwnerCompanyPage(@Param("companyPageId") Long companyPageId, Pageable pageable);
}

