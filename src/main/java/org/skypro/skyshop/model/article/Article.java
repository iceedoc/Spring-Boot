package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String text;

    public Article(UUID id, String title, String text) {
        if (id == null) {
            throw new IllegalArgumentException("ID статьи не может быть null");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название статьи не может быть пустым");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст статьи не может быть пустым");
        }
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return title + " " + text;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("%s%n%s", title, text);
    }
}