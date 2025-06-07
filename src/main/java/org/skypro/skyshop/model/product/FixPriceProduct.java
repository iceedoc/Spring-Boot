package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private final static double FIX_PRICE = 1_000;

    public FixPriceProduct(UUID id, String productName) {
        super(id, productName);
    }

    public FixPriceProduct(String productName) {
        this(UUID.randomUUID(), productName);
    }

    @Override
    public double getPrice() {
        return FIX_PRICE;
    }

    @Override
    public String getFormattedPrice() {
        return String.format("%,.2f", getPrice()).replace(',', '.');
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "c фиксированной ценой: " + "Фиксированная цена " + getFormattedPrice();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        FixPriceProduct object = (FixPriceProduct) other;
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), FIX_PRICE);
    }
}