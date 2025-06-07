package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productsMap;
    private final Map<UUID, Article> articlesMap;

    public StorageService() {
        this.productsMap = new HashMap<>();
        this.articlesMap = new HashMap<>();
        createData();
    }

    public Collection<Product> getAllProducts() {
        return productsMap.values();
    }

    public Collection<Article> getAllArticles() {
        return articlesMap.values();
    }

    public Collection<Searchable> getAllSearchableResults() {
        List<Searchable> results = new ArrayList<>();
        results.addAll(productsMap.values());
        results.addAll(articlesMap.values());
        return results;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productsMap.get(id));
    }

    private void createData() {
        List<Product> testProducts = Arrays.asList(
                new DiscountedProduct("Мышь", 1000, 5),
                new DiscountedProduct("Клавиатура", 5_000, 13),
                new DiscountedProduct("Часы", 1_500, 10),
                new DiscountedProduct("Смартфон", 8_000, 5),
                new FixPriceProduct("Роутер"),
                new FixPriceProduct("Коммутатор"),
                new FixPriceProduct("Камера"),
                new SimpleProduct("Принтер", 15_000),
                new SimpleProduct("Монитор", 20_000),
                new SimpleProduct("САБ", 32_000),
                new SimpleProduct("Наушники беспроводные", 6_000),
                new SimpleProduct("Приставка игровая", 30_000),
                new SimpleProduct("Операционная система", 35_000)
        );
        for (Product product : testProducts) {
            productsMap.put(product.getId(), product);
        }
        List<Article> testArticles = Arrays.asList(
                new Article("Бытовые устройства", "Телевизор"),
                new Article("Аудиотехника", "Колонки"),
                new Article("Бытовые устройства", "Смартфон"),
                new Article("Бытовые устройства", "Планшет"),
                new Article("Аудиотехника", "Наушники"),
                new Article("Устройства ввода", "Мышь"),
                new Article("Аудиотехника", "САБ"),
                new Article("Устройства ввода", "Клавиатура"),
                new Article("Сетевые устройства", "Роутер"),
                new Article("Устройства вывода", "Монитор"),
                new Article("Умные устройства", "Датчики"),
                new Article("Умные устройства", "Системы авторизации дома"),
                new Article("Процессоры", "Процессор Intel Core i9 13900 K")
        );
        for (Article article : testArticles) {
            articlesMap.put(article.getId(), article);
        }
    }
}