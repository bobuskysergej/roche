package com.roche;

import com.roche.domain.Product;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class TestUtility {

    private static Random random = new Random();

    public static Product mockProduct() {
        return Product.builder()
                .sku(UUID.randomUUID().toString())
                .name(randomName())
                .created(Instant.now())
                .price(randomPrice())
                .build();
    }

    private static String randomName() {
        return "product-" + random.nextInt();
    }

    private static Double randomPrice() {
        return random.nextDouble();
    }
}
