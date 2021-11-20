package com.aybu9.aybualumni.page.repositories;

import com.aybu9.aybualumni.page.models.CommunitySector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunitySectorRepository extends JpaRepository<CommunitySector, Integer> {
}
