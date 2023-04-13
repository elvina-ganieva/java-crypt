package org.example;

import java.security.SecureRandom;
import java.util.Random;

public class RandomizerFactory {

    static Random getRandomizer(String mode) {
        if (mode.equals("Basic")) {
            return new Random();
        } else if (mode.equals("Secure")) {
            return new SecureRandom();
        } else {
            throw new IllegalArgumentException("Неизвестный параметр: " + mode);
        }
    }
}
