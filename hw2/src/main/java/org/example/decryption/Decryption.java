package org.example.decryption;

import org.example.common.CipherService;
import org.example.common.DataRepository;
import org.example.common.KeyInitializer;

import javax.crypto.*;

public class Decryption {
    public static void main(String[] args) {
        var repo = new DataRepository();
        var hashCode = repo.getHashCode();
        var cipheredWord = repo.getCipheredWord();


        var key = new KeyInitializer().getKey();
        var decryptedWord = new CipherService().cipher(Cipher.DECRYPT_MODE, key, cipheredWord);

        var result = new String(decryptedWord);

        if (result.hashCode() == hashCode) {
            System.out.println("hashes are equal");
        } else {
            System.out.println("KO");
        }
        System.out.println("result: " + result);
    }
}
