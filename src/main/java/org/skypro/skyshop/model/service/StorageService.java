package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> productMap;
    private final Map<UUID, Article> articleMap;

    public StorageService(Map<UUID, Product> productMap, Map<UUID, Article> articleMap) {
        this.productMap = productMap;
        this.articleMap = articleMap;
        addArticle();
        addProduct();
    }

    public Map<UUID, Product> getProductMap() {
        return productMap;
    }

    public Map<UUID, Article> getArticleMap() {
        return articleMap;
    }

    private void addProduct() {
        SimpleProduct apple = new SimpleProduct("Яблоки", 100, UUID.randomUUID());
        FixPriceProduct razor = new FixPriceProduct("Бритва", UUID.randomUUID());
        SimpleProduct cookie = new SimpleProduct("Печенье", 200, UUID.randomUUID());
        DiscountedProduct meat = new DiscountedProduct("Мясо", 777, 25, UUID.randomUUID());
        SimpleProduct milk = new SimpleProduct("Молоко", 150, UUID.randomUUID());
        SimpleProduct salt = new SimpleProduct("Соль", 50, UUID.randomUUID());
        productMap.put(apple.getId(), apple);
        productMap.put(razor.getId(), razor);
        productMap.put(cookie.getId(), cookie);
        productMap.put(meat.getId(), meat);
        productMap.put(milk.getId(), milk);
        productMap.put(salt.getId(), salt);
    }
    private void addArticle() {
        Article appleAbout = new Article("Яблоки", "Свежие яблоки", UUID.randomUUID());
        Article razorAbout = new Article("Бритва", "Острая бритва", UUID.randomUUID());
        Article cookieAbout = new Article("Печенье", "Хрустящее печенье", UUID.randomUUID());
        Article meatAbout = new Article("Мясо", "Говяжье", UUID.randomUUID());
        Article milkAbout = new Article("Молоко", "Пастеризованное", UUID.randomUUID());
        Article saltAbout = new Article("Соль", "Йодированная", UUID.randomUUID());
        articleMap.put(appleAbout.getId(), appleAbout);
        articleMap.put(razorAbout.getId(), razorAbout);
        articleMap.put(cookieAbout.getId(), cookieAbout);
        articleMap.put(meatAbout.getId(), meatAbout);
        articleMap.put(milkAbout.getId(), milkAbout);
        articleMap.put(saltAbout.getId(), saltAbout);
    }

    public Collection<Searchable> searchableCollection() {
        return Stream.concat(
                        productMap.values().stream(),
                        articleMap.values().stream()
                )
                .collect(Collectors.toList());
    }
}