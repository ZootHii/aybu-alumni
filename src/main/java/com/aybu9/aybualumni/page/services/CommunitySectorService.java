package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunitySector;

public interface CommunitySectorService {
    DataResult<CommunitySector> get(Integer communitySectorId);
}
