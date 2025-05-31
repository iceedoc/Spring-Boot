package org.skypro.skyshop.service;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initTestData();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> all = new ArrayList<>();
        all.addAll(products.values());
        all.addAll(articles.values());
        return all;
    }

    private void initTestData() {
        UUID laptopId = UUID.randomUUID();
        products.put(laptopId, new Product(laptopId, "Телефон") {
            @Override
            public double getPrice() {
                return 999.99;
            }

            @Override
            public boolean isSpecial() {
                return false;
            }
        });
        UUID yogurtId = UUID.randomUUID();
        products.put(yogurtId, new Product(yogurtId, "Квас") {
            @Override
            public double getPrice() {
                return 99.90;
            }

            @Override
            public boolean isSpecial() {
                return true;
            }
        });
        UUID bookId = UUID.randomUUID();
        products.put(bookId, new Product(bookId, "Книга 'Java для начинающих'") {
            @Override
            public double getPrice() {
                return 1000.00;
            }

            @Override
            public boolean isSpecial() {
                return false;
            }
        });
        UUID articleId = UUID.randomUUID();
        articles.put(articleId, new Article(articleId, "Новости технологий", "ИИ научился писать код..."));
    }
}