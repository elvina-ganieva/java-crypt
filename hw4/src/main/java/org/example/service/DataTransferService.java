package org.example.service;


import org.example.dto.Dto;

import java.io.*;

public class DataTransferService {

    private static final String DTO_STORAGE = "dto-storage.txt";

    public void writeObject(Dto dto) {
        try (var objectOutputStream = new ObjectOutputStream(new FileOutputStream(DTO_STORAGE))) {
            objectOutputStream.writeObject(dto);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить ДТО в файл.", e);
        }
    }

    public Dto readObject() {
        try (var objectInputStream = new ObjectInputStream(new FileInputStream(DTO_STORAGE))) {
            return (Dto) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Не удалось считать ДТО из файла.", e);
        }
    }
}
