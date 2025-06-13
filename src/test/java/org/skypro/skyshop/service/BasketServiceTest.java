package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BasketServiceTest {
    private ProductBasket productBasketMock;
    private StorageService storageServiceMock;
    private BasketService basketService;

    @BeforeEach
    public void setUp() {
        productBasketMock = mock(ProductBasket.class);
        storageServiceMock = mock(StorageService.class);
        basketService = new BasketService(productBasketMock, storageServiceMock);
    }

    @Test
    public void testAdd_NonExistentProductThrowsAnException() {
        UUID falseId = UUID.randomUUID();

        when(storageServiceMock.getProductByIdOrThrow(falseId)).thenThrow(new NoSuchProductException(falseId.toString()));

        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(falseId));

        verify(storageServiceMock).getProductByIdOrThrow(falseId);
        verifyNoInteractions(productBasketMock);
    }

    @Test
    public void testAdd_ExistingProductCallsAddProduct() {
        UUID productId = UUID.randomUUID();

        Product mockProduct = mock(Product.class);

        when(storageServiceMock.getProductByIdOrThrow(productId)).thenReturn(mockProduct);

        basketService.addProduct(productId);

        verify(productBasketMock).addProducts(productId);
        verify(storageServiceMock).getProductByIdOrThrow(productId);
    }

    @Test
    public void testGetUserBasketEmpty() {
        when(productBasketMock.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket userBasket = basketService.getUserBasket();

        assertTrue(userBasket.getBasketItems().isEmpty());
    }

    @Test
    public void testGetUserBasketWithItems() {
        UUID productId = UUID.randomUUID();

        Map<UUID, Integer> productsMap = Map.of(productId, 3);
        when(productBasketMock.getProducts()).thenReturn(productsMap);

        Product mockProduct = mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(100.00);

        when(storageServiceMock.getProductByIdOrThrow(productId)).thenReturn(mockProduct);

        UserBasket basket = basketService.getUserBasket();

        assertEquals(1, basket.getBasketItems().size());

        BasketItem item = basket.getBasketItems().get(0);
        assertEquals(mockProduct, item.getProduct());
        assertEquals(3, item.getNumberOfProducts());

    }

    @Test
    public void testAdd_WithNullIdProduct() {
        assertThrows(IllegalArgumentException.class, () -> basketService.addProduct(null));
        verifyNoInteractions(storageServiceMock);
    }

}

