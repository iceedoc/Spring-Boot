package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int price;

    public SimpleProduct(UUID id, String productName, int price) {
        super(id, productName);
        if (price <= 0) {
            throw new IllegalArgumentException("Установите цену товара, она не может быть отрицательной или равна нулю.");
        }
        this.price = price;
    }

    public SimpleProduct(String productName, int price) {
        this(UUID.randomUUID(), productName, price);
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getFormattedPrice() {
        return String.format("%,.2f", getPrice()).replace(',', '.');
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + getFormattedPrice();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        SimpleProduct object = (SimpleProduct) other;
        return super.equals(object) && price == object.price;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), price);
    }
}