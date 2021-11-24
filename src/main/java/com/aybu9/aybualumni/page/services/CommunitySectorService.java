package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CommunitySector;

import java.util.Collection;

public interface CommunitySectorService {

    DataResult<Collection<CommunitySector>> getAll();


    DataResult<CommunitySector> get(Integer communitySectorId);
}
