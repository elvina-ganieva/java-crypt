package org.example;

import org.example.common.DataTransferService;
import org.example.service.*;

import javax.crypto.Cipher;

public class Decryption {

    public static void main(String[] args) {
        var dto = new DataTransferService().readObject();

        var privateKey = new KeyStoreService().getPrivateKey();

        var decipheredData = new CipherService().cipher(Cipher.DECRYPT_MODE, privateKey, dto.cipheredText());
        System.out.println("Расшифрованный текст: " + new String(decipheredData));

        var isSignOk = new SignatureService().verifySignature(dto.cipheredText(), dto.publicKey(), dto.signature());
        if (isSignOk) {
            System.out.println("Sign is ok.");
        }
    }
}
