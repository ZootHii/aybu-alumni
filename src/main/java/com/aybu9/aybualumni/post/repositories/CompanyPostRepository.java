package com.aybu9.aybualumni.post.repositories;

import com.aybu9.aybualumni.post.models.CompanyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPostRepository extends JpaRepository<CompanyPost, Long> {
}

