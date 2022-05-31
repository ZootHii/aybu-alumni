package com.aybu9.aybualumni.search.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.page.repositories.CommunityPageRepository;
import com.aybu9.aybualumni.page.repositories.CompanyPageRepository;
import com.aybu9.aybualumni.search.models.SearchRequest;
import com.aybu9.aybualumni.search.models.SearchResult;
import com.aybu9.aybualumni.user.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class SearchManager implements SearchService {

    private final CommunityPageRepository communityPageRepository;
    private final CompanyPageRepository companyPageRepository;
    private final UserRepository userRepository;

    public SearchManager(CommunityPageRepository communityPageRepository,
                         CompanyPageRepository companyPageRepository,
                         UserRepository userRepository) {
        this.communityPageRepository = communityPageRepository;
        this.companyPageRepository = companyPageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DataResult<Collection<SearchResult>> search(SearchRequest searchRequest) {
        var name = searchRequest.getName();
        var pageRequest = PageRequest.of(0, searchRequest.getSize());

        var communityPages = communityPageRepository
                .searchByNameContainsOrStartsWith(name, pageRequest).getContent();
        var companyPages = companyPageRepository
                .searchByNameContainsOrStartsWith(name, pageRequest).getContent();
        var users = userRepository
                .searchByNameContainsOrStartsWith(name, pageRequest).getContent();
        var searchResults = new ArrayList<SearchResult>();

        var communitySearchResults = communityPages.stream()
                .map(communityPage ->
                        new SearchResult(communityPage.getPage().getName(),
                                communityPage.getPage().getLogoPhotoUrl(),
                                "Community",
                                communityPage.getCommunitySector().getName(),
                                communityPage.getPage().getPageUrl())).toList();

        var companySearchResults = companyPages.stream()
                .map(companyPage ->
                        new SearchResult(companyPage.getPage().getName(),
                                companyPage.getPage().getLogoPhotoUrl(),
                                "Company",
                                companyPage.getCompanySector().getName(),
                                companyPage.getPage().getPageUrl())).toList();

        var userSearchResults = users.stream()
                .map(user ->
                        new SearchResult(user.getName() + " " + user.getSurname(),
                                user.getProfilePhotoUrl(),
                                "User",
                                user.getHeadline(),
                                user.getProfileUrl())).toList();

        Stream.of(communitySearchResults, companySearchResults, userSearchResults)
                .forEach(searchResults::addAll);

        return new SuccessDataResult<>(searchResults, "search");
    }
}
