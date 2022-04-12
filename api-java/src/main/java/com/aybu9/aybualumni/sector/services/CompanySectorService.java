package com.aybu9.aybualumni.sector.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.sector.models.CompanySector;

import java.util.Collection;

public interface CompanySectorService {

    DataResult<Collection<CompanySector>> getAll(); // TODO: 24.11.2021 sectorler page gibi tek service ve manager altında birleştir 

    DataResult<CompanySector> get(Integer companySectorId);
}
