package com.aybu9.aybualumni.fake_obs.repositories;

import com.aybu9.aybualumni.fake_obs.models.FakeOBSData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FakeOBSDataRepository extends JpaRepository<FakeOBSData, String> {
    
}
