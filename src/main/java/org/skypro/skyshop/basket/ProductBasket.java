package org.skypro.skyshop.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer>  productBasketMap = new HashMap<>();

    public void addProduct(UUID id) {
        productBasketMap.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getItems() {
        return Collections.unmodifiableMap(productBasketMap);
    }
}
