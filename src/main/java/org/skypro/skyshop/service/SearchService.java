package org.skypro.skyshop.service;

import org.skypro.skyshop.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String searchTerm) {
        return storageService.searchableCollection().stream()
                .filter(searchable ->
                        searchable.searchableTerm().toLowerCase()
                                .contains(searchTerm.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}