package org.skypro.skyshop.model.exception;

import java.util.UUID;

public class NoSuchProductException extends RuntimeException {
    private final UUID id;


    public NoSuchProductException(UUID id) {
        super("Товар не найден: " + id);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
