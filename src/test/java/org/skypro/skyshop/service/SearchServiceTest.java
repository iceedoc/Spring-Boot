package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceTest {
    private StorageService storageServiceMock;
    private SearchService searchService;

    @BeforeEach
    public void setUp() {
        storageServiceMock = mock(StorageService.class);
        searchService = new SearchService(storageServiceMock);
    }

    private Searchable createSearchable(String term, String type, String id) {
        return new Searchable() {
            @Override
            public String getSearchTerm() {
                return term;
            }

            @Override
            public String getTypeOfContent() {
                return type;
            }

            @Override
            public UUID getId() {
                return UUID.fromString(id);
            }
        };
    }

    @Test
    public void testSearch_NoObjectsInStorage() {
        String query = "Electronic book";

        when(storageServiceMock.getAllSearchableResults()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search(query);
        assertTrue(results.isEmpty(), "Результат поиска должен быть пустым, т.к. в Storage не должно быть объектов");
    }

    @Test
    public void testSearch_NoMatchingObjects() {
        String query = "Electronic book";
        Searchable article = createSearchable("Soft", "ARTICLE", "00000000-0000-0000-0000-000000000001");
        Searchable product = createSearchable("Laptop", "PRODUCT", "00000000-0000-0000-0000-000000000001");

        when(storageServiceMock.getAllSearchableResults()).thenReturn(Arrays.asList(article, product));

        Collection<SearchResult> results = searchService.search(query);
        assertTrue(results.isEmpty(), "Результат поиска должен быть пустым, т.к. совпадений нет");
    }

    @Test
    public void testSearch_WithMatchingObject() {
        String query = "Laptop";
        Searchable article = createSearchable("Soft", "ARTICLE", "00000000-0000-0000-0000-000000000001");
        Searchable product = createSearchable("Laptop", "PRODUCT", "00000000-0000-0000-0000-000000000002");

        when(storageServiceMock.getAllSearchableResults()).thenReturn(Arrays.asList(article, product));

        Collection<SearchResult> results = searchService.search(query);
        assertEquals(1, results.size(), "Должен быть найден один результат");
        SearchResult result = results.iterator().next();
        assertEquals("Laptop", result.getName());
        assertEquals("PRODUCT", result.getContentType());
        assertEquals("00000000-0000-0000-0000-000000000002", result.getId());
    }

    @Test
    public void testSearch_MultipleMatchingObjects() {
        String query = "soft";
        Searchable article1 = createSearchable("Windows 7 (Soft)", "ARTICLE", "00000000-0000-0000-0000-000000000001");
        Searchable article2 = createSearchable("Windows 8 (Soft)", "ARTICLE", "00000000-0000-0000-0000-000000000002");
        Searchable product = createSearchable("Windows 7  Ultimate (Soft)", "PRODUCT", "00000000-0000-0000-0000-000000000003");

        when(storageServiceMock.getAllSearchableResults()).thenReturn(Arrays.asList(article1, article2, product));

        Collection<SearchResult> results = searchService.search(query);
        assertEquals(3, results.size(), "Должны быть найдены все 3 объекта т.к. содержат soft");
    }

    @Test
    public void testSearch_WithNullOrEmptyQuery() {
        assertThrows(IllegalArgumentException.class, () -> searchService.search(""));
        assertThrows(IllegalArgumentException.class, () -> searchService.search(null));
    }
}
