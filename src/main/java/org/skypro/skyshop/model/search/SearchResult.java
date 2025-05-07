package org.skypro.skyshop.model.search;

import java.util.UUID;

public class SearchResult {
    public final String id;
    public final String name;
    public final String contentType;

    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
                searchable.getId().toString(),
                searchable.searchableName(),
                searchable.searchableType()
        );
    }
}