package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.City;

public interface CityService {
    DataResult<City> get(Integer cityId);
}
