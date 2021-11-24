package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.page.models.City;

import java.util.Collection;

public interface CityService {
    
    DataResult<Collection<City>> getAll();
    
    DataResult<City> get(Integer cityId);
}
