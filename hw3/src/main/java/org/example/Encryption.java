package org.example;

import org.example.common.DataTransferService;
import org.example.dto.Dto;
import org.example.service.*;

import javax.crypto.Cipher;

public class Encryption {

    public static void main(String[] args) {
        var key = new KeyGenerator(new KeyStoreService()).generateKey();

        var cipheredData = new CipherService().cipher(Cipher.ENCRYPT_MODE, key.getPublic(), "Java".getBytes());

        var signature = new SignatureService().signData(cipheredData, key.getPrivate());

        new DataTransferService().writeObject(new Dto(cipheredData, key.getPublic(), signature));
    }
}
