package org.example.service;

import org.example.enums.KeyStoreType;
import org.example.enums.Mode;

import java.security.SecureRandom;
import java.util.Random;

public class RandomService {

    public KeyStoreType getRandomKeyStoreType(String mode) {
        var random = getRandom(mode);
        int arrayIndex = random.nextInt(0, KeyStoreType.values().length);
        return KeyStoreType.values()[arrayIndex];
    }

    private Random getRandom(String mode) {
        if (Mode.BASIC.toString().equalsIgnoreCase(mode)) {
            return new Random(System.nanoTime());
        } else if (Mode.SECURE.toString().equalsIgnoreCase(mode)) {
            return new SecureRandom(new byte[]{(byte) System.nanoTime()});
        } else {
            throw new RuntimeException("Способ получения рандома должен быть либо Basic, либо Secure. Текущий способ: " + mode);
        }
    }
}
