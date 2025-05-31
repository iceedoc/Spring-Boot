package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            return List.of();
        }
        String lower = pattern.toLowerCase();
        return storageService.getAllSearchables().stream()
                .filter(s -> s.getName().toLowerCase().contains(lower))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}