package org.example.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestService {

    private static final String DIGEST_ALGORITHM = "SHA-256";

    public byte[] digestMessage(byte[] data) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось получить экземпляр MessageDigest с алгоритмом: " + DIGEST_ALGORITHM, e);
        }
        return messageDigest.digest(data);
    }
}
