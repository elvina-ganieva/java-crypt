package org.example.service;

import java.security.*;

public class SignatureService {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public byte[] signData(byte[] data, PrivateKey privateKey) {
        try {
            var signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException("Не удалось создать подпись.", e);
        }
    }

    public boolean verifySignature(byte[] digitalSignature, byte[] data, PublicKey publicKey) {
        try {
            var signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(digitalSignature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("Не удалось проверить подпись.", e);
        }
    }
}
