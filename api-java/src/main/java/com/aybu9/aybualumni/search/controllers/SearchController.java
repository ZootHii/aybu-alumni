package com.aybu9.aybualumni.search.controllers;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.search.models.SearchRequest;
import com.aybu9.aybualumni.search.models.SearchResult;
import com.aybu9.aybualumni.search.services.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<DataResult<Collection<SearchResult>>> search(
            @Valid @RequestBody SearchRequest searchRequest) {
        var result = searchService.search(searchRequest);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
