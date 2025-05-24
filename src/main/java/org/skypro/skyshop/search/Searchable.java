package org.skypro.skyshop.search;

import java.util.UUID;

public interface Searchable {
    String searchableTerm();

    String searchableType();

    String searchableName();

    default void getStringRepresentation(){
        System.out.println(searchableName() + " — " + searchableType());
    }
    UUID getId();
}