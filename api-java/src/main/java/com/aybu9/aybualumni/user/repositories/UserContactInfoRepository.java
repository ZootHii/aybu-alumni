package com.aybu9.aybualumni.user.repositories;

import com.aybu9.aybualumni.user.models.UserContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactInfoRepository extends JpaRepository<UserContactInfo, Long> {
}
