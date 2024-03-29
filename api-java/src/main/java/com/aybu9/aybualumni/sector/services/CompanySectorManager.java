package com.aybu9.aybualumni.sector.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.sector.models.CompanySector;
import com.aybu9.aybualumni.sector.repositories.CompanySectorRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.aybu9.aybualumni.core.result.Constants.GetAllSuccess;

@Service
public class CompanySectorManager implements CompanySectorService {

    private final CompanySectorRepository companySectorRepository;

    public CompanySectorManager(CompanySectorRepository companySectorRepository) {
        this.companySectorRepository = companySectorRepository;
    }

    @Override
    public DataResult<Collection<CompanySector>> getAll() {
        return new SuccessDataResult<>(companySectorRepository.findAll(), GetAllSuccess);
    }

    @Override
    public DataResult<CompanySector> get(Integer companySectorId) {
        var companySector = companySectorRepository.findById(companySectorId)
                .orElseThrow(() -> new CustomException("not found company sector"));
        return new SuccessDataResult<>(companySector, "company sector returned");
    }
}
