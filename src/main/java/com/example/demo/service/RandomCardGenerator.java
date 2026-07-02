package com.example.demo.service;

import java.util.Random;

public class RandomCardGenerator {

    private static final Random RANDOM = new Random();

    public static String generateCardNumber() {

        StringBuilder sb = new StringBuilder("5");

        while (sb.length() < 16) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }

    public static String generateProfileNumber() {

        StringBuilder sb = new StringBuilder();

        while (sb.length() < 10) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }

    public static int randomBalance() {

        // Between R500 and R5000
        return (500 + RANDOM.nextInt(4501)) * 100;

    }

}
