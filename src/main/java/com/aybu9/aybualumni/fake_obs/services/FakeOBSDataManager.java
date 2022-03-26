package com.aybu9.aybualumni.fake_obs.services;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.fake_obs.models.FakeOBSData;
import com.aybu9.aybualumni.fake_obs.repositories.FakeOBSDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.aybu9.aybualumni.core.result.Constants.GetAllSuccess;

@Service
public class FakeOBSDataManager implements FakeOBSDataService {

    private final FakeOBSDataRepository fakeOBSDataRepository;

    public FakeOBSDataManager(FakeOBSDataRepository fakeOBSDataRepository) {
        this.fakeOBSDataRepository = fakeOBSDataRepository;
    }

    @Override
    public DataResult<Collection<FakeOBSData>> getAll() {
        return new SuccessDataResult<>(fakeOBSDataRepository.findAll(), GetAllSuccess);
    }

    @Override
    @Transactional
    public DataResult<FakeOBSData> get(String tcIdentityNumber) {
        var fakeOBSData = fakeOBSDataRepository.findById(tcIdentityNumber)
                .orElseThrow(() -> new CustomException("not found obs data"));
        return new SuccessDataResult<>(fakeOBSData, "obs data returned");
    }
}
