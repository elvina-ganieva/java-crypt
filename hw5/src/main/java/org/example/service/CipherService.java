package org.example.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CipherService {

    private static final String CIPHER_TRANSFORMATION = "RSA";

    public byte[] cipher(int mode, Key key, byte[] data) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException("Не удалось преобразовать данные.", e);
        }
    }
}
