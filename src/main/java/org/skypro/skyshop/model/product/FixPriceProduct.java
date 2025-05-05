package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIX_PRICE = 500;

    public FixPriceProduct(String name, UUID id) {
        super(name);
        this.id = id;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public int getPrice() {
        return FIX_PRICE;
    }

    @Override
    public String toString() {
        return super.getName() + ": Фиксированная цена " + FIX_PRICE;
    }
}
