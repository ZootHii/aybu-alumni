package com.aybu9.aybualumni.event.repositories;

import com.aybu9.aybualumni.event.models.CommunityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityEventRepository extends JpaRepository<CommunityEvent, Long> {
}