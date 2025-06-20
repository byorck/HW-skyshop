package org.skypro.skyshop.service;

import org.skypro.skyshop.basket.BasketItem;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.basket.UserBasket;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.product.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class BasketService {

    private final ProductBasket basket;
    private final StorageService storage;

    public BasketService(ProductBasket basket, StorageService storage) {
        this.basket = basket;
        this.storage = storage;
    }

    public void addProduct(UUID id) {
        if (storage.getProductById(id).isEmpty()) {
            throw new NoSuchProductException(id);
        }
        basket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> itemsMap = basket.getItems();
        List<BasketItem> items = new ArrayList<>();
        int total = 0;

        for (Map.Entry<UUID, Integer> entry : itemsMap.entrySet()) {
            UUID productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = storage.getProductById(productId)
                    .orElseThrow(() -> new NoSuchProductException(productId));

            items.add(new BasketItem(product, quantity));
            total += product.getPrice() * quantity;
        }

        return new UserBasket(items, total);
    }
}
