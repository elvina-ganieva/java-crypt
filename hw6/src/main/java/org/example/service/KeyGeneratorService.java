package org.example.service;

import sun.security.tools.keytool.CertAndKeyGen;

import java.security.NoSuchAlgorithmException;

public class KeyGeneratorService {

    private static final int KEY_SIZE = 512;
    private static final String KEY_TYPE = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    public CertAndKeyGen getCertAndKeyGenerator() {
        sun.security.tools.keytool.CertAndKeyGen generator;
        try {
            generator = new CertAndKeyGen(KEY_TYPE, SIGNATURE_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Неизвестный алгоритм: " + KEY_TYPE, e);
        }
        generator.generate(KEY_SIZE);
        return generator;
    }
}
