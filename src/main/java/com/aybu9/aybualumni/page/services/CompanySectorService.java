package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.CompanySector;

public interface CompanySectorService {
    DataResult<CompanySector> get(Integer companySectorId);
}
