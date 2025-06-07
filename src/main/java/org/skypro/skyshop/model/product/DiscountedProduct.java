package org.skypro.skyshop.model.product;


import java.util.UUID;

public class DiscountedProduct extends Product {
    private final double basePrice;
    private final int percentageDiscount;
    private static final int PERCENT = 100;

    public DiscountedProduct(UUID id, String productName, int basePrice, int percentageDiscount) {
        super(id, productName);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Установите цену товара, она не может быть отрицательной или равна нулю.");
        }
        if (percentageDiscount < 0 || percentageDiscount > 100) {
            throw new IllegalArgumentException("Процент скидки может быть только от 0 до 100 включительно.");
        }
        this.basePrice = basePrice;
        this.percentageDiscount = percentageDiscount;
    }

    public DiscountedProduct(String productName, int basePrice, int percentageDiscount) {
        super(UUID.randomUUID(), productName);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Установите цену товара, она не может быть отрицательной или равна нулю.");
        }
        if (percentageDiscount < 0 || percentageDiscount > 100) {
            throw new IllegalArgumentException("Процент скидки может быть только от 0 до 100 включительно.");
        }
        this.basePrice = basePrice;
        this.percentageDiscount = percentageDiscount;

    }

    @Override
    public double getPrice() {
        return basePrice - (basePrice * percentageDiscount / (double) PERCENT);
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
        return super.toString() + "со скидкой: " + getFormattedPrice() + " (" + percentageDiscount + "%" + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        DiscountedProduct object = (DiscountedProduct) other;
        return super.equals(object) && Double.compare(basePrice, object.basePrice) == 0 &&
                percentageDiscount == object.percentageDiscount;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), basePrice, percentageDiscount);
    }
}