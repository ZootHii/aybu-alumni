package com.aybu9.aybualumni.fake_obs.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.fake_obs.models.FakeOBSData;
import com.aybu9.aybualumni.sector.models.CommunitySector;

import java.util.Collection;

public interface FakeOBSDataService {

    DataResult<Collection<FakeOBSData>> getAll();
    
    DataResult<FakeOBSData> get(String tcIdentityNumber);
}
