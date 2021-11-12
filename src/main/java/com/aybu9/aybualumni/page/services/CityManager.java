package com.aybu9.aybualumni.page.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.page.models.City;
import com.aybu9.aybualumni.page.repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityManager implements CityService {

    private final CityRepository cityRepository;

    public CityManager(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public DataResult<City> get(Integer companySectorId) {
        var city = cityRepository.findById(companySectorId);
        if (city.isEmpty()) {
            throw new CustomException("not found city");
        }
        return new SuccessDataResult<>(city.get(), "city returned");
    }
}
