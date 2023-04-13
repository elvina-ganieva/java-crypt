package org.example;

import java.security.SecureRandom;
import java.util.Random;

public class RandomizerFactory {

    static Random getRandomizer(String mode) {
        if (mode.equals("Basic")) {
            return new Random(System.nanoTime());
        } else if (mode.equals("Secure")) {
            return new SecureRandom(new byte[]{(byte) System.nanoTime()});
        } else {
            throw new IllegalArgumentException("Неизвестный параметр: " + mode);
        }
    }
}
