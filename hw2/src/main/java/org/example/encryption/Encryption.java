package org.example.encryption;

import org.example.common.CipherService;
import org.example.common.DataRepository;
import org.example.common.KeyInitializer;

import javax.crypto.Cipher;
import java.util.Arrays;

public class Encryption {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Необходимо секретное слово в параметрах.");
            return;
        }

        var secretWord = args[0];
        var hashOfSecretWord = secretWord.hashCode();

        var key = new KeyInitializer().initializeKey();
        var cipheredSecretWord = new CipherService().cipher(Cipher.ENCRYPT_MODE, key, secretWord.getBytes());

        System.out.printf("hash: %s%nciphered word: %s%n", hashOfSecretWord, Arrays.toString(cipheredSecretWord));

        var repo = new DataRepository();
        repo.saveHashCode(hashOfSecretWord);
        repo.saveCipheredWord(cipheredSecretWord);
    }
}
