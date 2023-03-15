package com.example.cookbook;

import java.util.UUID;

public class RandomKeyGenerator {
    public static String generateRandomKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}