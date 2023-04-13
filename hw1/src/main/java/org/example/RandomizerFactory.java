package org.example;

import java.security.SecureRandom;
import java.util.Random;

public class RandomizerFactory {

    static Random getRandomizer(String param) {
        if (param.equals("Basic")) {
            return new Random();
        } else if (param.equals("Secure")) {
            return new SecureRandom();
        } else {
            throw new IllegalArgumentException("Неизвестный параметр: " + param);
        }
    }
}
