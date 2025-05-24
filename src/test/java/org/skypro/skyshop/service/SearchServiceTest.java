package org.skypro.skyshop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.article.Article;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.search.SearchResult;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("Поиск в случае отсутствия объектов в StorageService")
    public void givenEmptyStorage_whenFind_thenReturnEmptyList() {
        when(storageService.searchableCollection()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("test");

        assertTrue(results.isEmpty());

        verify(storageService, Mockito.times(1)).searchableCollection();
    }

    @Test
    @DisplayName("Поиск в случае, если объекты в StorageService есть, но нет подходящего")
    void search_WhenNoMatches_ShouldReturnEmptyList() {
        //given
        Product createTestProduct = new SimpleProduct("Телефон", 1000, UUID.randomUUID());
        Article createTestArticle  = new Article("Ноутбук", "Описание ноутбука", UUID.randomUUID());

        //when
        when(storageService.searchableCollection())
                .thenReturn(Stream.of(createTestProduct, createTestArticle).collect(Collectors.toList()));

        Collection<SearchResult> results = searchService.search("Холодильник");

        //then
        assertTrue(results.isEmpty());

        //verify
        verify(storageService, Mockito.times(1)).searchableCollection();
    }

    @Test
    @DisplayName("Поиск, когда есть подходящий объект в StorageService")
    void search_WhenMatchesExist_ShouldReturnResults() {
        Product createTestProduct1 = new SimpleProduct ("Молоко", 100, UUID.randomUUID());
        Product createTestProduct2 = new SimpleProduct ("Шоколад", 150, UUID.randomUUID());
        Article createTestArticle  = new Article ("Молочные продукты", "Статья о молоке", UUID.randomUUID());

        when(storageService.searchableCollection())
                .thenReturn(Stream.of(createTestProduct1, createTestProduct2, createTestArticle).collect(Collectors.toList()));

        Collection<SearchResult> results = searchService.search("молок");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.name.equals("Молоко")));
        assertTrue(results.stream().anyMatch(r -> r.name.equals("Молочные продукты")));

        verify(storageService, Mockito.times(1)).searchableCollection();
    }
}


