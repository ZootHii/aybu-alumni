package com.aybu9.aybualumni.sector.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.sector.models.CommunitySector;
import com.aybu9.aybualumni.sector.repositories.CommunitySectorRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.aybu9.aybualumni.core.result.Constants.GetAllSuccess;

@Service
public class CommunitySectorManager implements CommunitySectorService {

    private final CommunitySectorRepository communitySectorRepository;

    public CommunitySectorManager(CommunitySectorRepository communitySectorRepository) {
        this.communitySectorRepository = communitySectorRepository;
    }

    @Override
    public DataResult<Collection<CommunitySector>> getAll() {
        return new SuccessDataResult<>(communitySectorRepository.findAll(), GetAllSuccess);
    }

    @Override
    public DataResult<CommunitySector> get(Integer communitySectorId) {
        var communitySector = communitySectorRepository.findById(communitySectorId);
        if (communitySector.isEmpty()) {
            throw new CustomException("not found community sector");
        }
        return new SuccessDataResult<>(communitySector.get(), "community sector returned");
    }
}
