package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.basket.BasketItem;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.basket.UserBasket;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.product.Product;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    private Product existingProduct;
    private UUID existingProductId;
    private UUID nonExistingProductId;

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        existingProduct = mock(Product.class);
        existingProductId = UUID.randomUUID();
        nonExistingProductId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Добавление несуществующего товара в корзину приводит к выбросу исключения")
    void addProduct_NonExistingProduct_ThrowsNoSuchProductException() {
        when(storageService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(nonExistingProductId));

        verify(productBasket, never()).addProduct(any());
    }

    @Test
    @DisplayName("Добавление существующего товара вызывает метод addProduct у мока ProductBasket")
    void addProduct_ExistingProduct_CallsAddProductOnProductBasket() {
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        basketService.addProduct(existingProductId);

        verify(productBasket, times(1)).addProduct(existingProductId);
    }

    @Test
    @DisplayName("Метод getUserBacket возвращает пустую корзину, если ProductBasket пуст")
    void getUserBasket_EmptyBasket_ReturnsEmptyUserBasket() {

        when(productBasket.getItems()).thenReturn(Collections.emptyMap());

        UserBasket userBasket = basketService.getUserBasket();

        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0, userBasket.getTotal());

        verify(productBasket, times(1)).getItems();
        verifyNoInteractions(storageService);
    }

    @Test
    @DisplayName("Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары")
    void getUserBasket_NonEmptyBasket_ReturnsCorrectUserBasket() {
        //given
        final int quantity = 3;
        final int pricePerProduct = 100;
        final int expectedTotal = quantity * pricePerProduct;
        Map<UUID, Integer> itemsMap = Collections.singletonMap(existingProductId, quantity);

        //when
        when(productBasket.getItems()).thenReturn(itemsMap);
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));
        when(existingProduct.getPrice()).thenReturn(pricePerProduct);

        UserBasket userBasket = basketService.getUserBasket();

        //then
        assertNotNull(userBasket);
        assertEquals(1, userBasket.getItems().size());
        assertEquals(expectedTotal, userBasket.getTotal());

        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(existingProduct, basketItem.getProduct());
        assertEquals(quantity, basketItem.getQuantity());

        //verify
        verify(productBasket, times(1)).getItems();
        verify(storageService, times(1)).getProductById(existingProductId);
    }
}

