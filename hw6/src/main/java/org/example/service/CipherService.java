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

    public byte[] cipher(byte[] data, Key key) {
        return process(data, key, Cipher.ENCRYPT_MODE);
    }

    public byte[] decipher(byte[] data, Key key) {
        return process(data, key, Cipher.DECRYPT_MODE);
    }

    private byte[] process(byte[] data, Key key, int mode) {
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
