package org.example;

import java.security.SecureRandom;
import java.util.Random;

interface RandomFactory {

    static Random getRandom(String mode) {
        if (Mode.BASIC.toString().equalsIgnoreCase(mode)) {
            return new Random(System.nanoTime());
        } else if (Mode.SECURE.toString().equalsIgnoreCase(mode)) {
            return new SecureRandom(new byte[]{(byte) System.nanoTime()});
        } else {
            throw new RuntimeException("Способ получения прогноза должен быть либо Basic, либо Secure. Текущий способ: " + mode);
        }
    }
}
