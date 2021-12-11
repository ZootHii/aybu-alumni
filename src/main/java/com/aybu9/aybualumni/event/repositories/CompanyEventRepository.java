package com.aybu9.aybualumni.event.repositories;

import com.aybu9.aybualumni.event.models.CompanyEvent;
import com.aybu9.aybualumni.page.models.CompanyPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEventRepository extends JpaRepository<CompanyEvent, Long> {
}
