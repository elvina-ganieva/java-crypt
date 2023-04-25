package org.example.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataAccessService {

    public void saveHashCode(int hashCode) {
        var pathForHash = Paths.get("hw2/hash.txt");
        try {
            Files.writeString(pathForHash, Integer.toString(hashCode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCipheredWord(byte[] cipheredWord) {
        var pathForBytes = Paths.get("hw2/ciphered-word.txt");
        try {
            Files.write(pathForBytes, cipheredWord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getHashCode() {
        var pathForHash = Paths.get("hw2/hash.txt");
        try {
            return Integer.parseInt(Files.readString(pathForHash));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getCipheredWord() {
        var pathForBytes = Paths.get("hw2/ciphered-word.txt");
        try {
            return Files.readAllBytes(pathForBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
