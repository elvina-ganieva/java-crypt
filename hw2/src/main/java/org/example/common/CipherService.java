package org.example.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class CipherService {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public byte[] cipher(int mode, Key key, byte []secretWord) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, key, new IvParameterSpec(new byte[16]));
            return cipher.doFinal(secretWord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
