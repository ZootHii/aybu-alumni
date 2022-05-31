package com.aybu9.aybualumni.search.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.search.models.SearchRequest;
import com.aybu9.aybualumni.search.models.SearchResult;

import java.util.Collection;

public interface SearchService {

    DataResult<Collection<SearchResult>> search(SearchRequest searchRequest);
}
