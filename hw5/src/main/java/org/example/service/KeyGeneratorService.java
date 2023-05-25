package org.example.service;

import sun.security.tools.keytool.CertAndKeyGen;

import java.security.NoSuchAlgorithmException;

public class KeyGeneratorService {

    private static final String KEY_TYPE = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    public CertAndKeyGen getCertAndKeyGenerator(int keyLength) {
        CertAndKeyGen generator;
        try {
            generator = new CertAndKeyGen(KEY_TYPE, SIGNATURE_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Неизвестный алгоритм: " + KEY_TYPE, e);
        }
        generator.generate(keyLength);
        return generator;
    }
}
