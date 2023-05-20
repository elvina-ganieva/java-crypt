package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Требуется 2 параметра на входе.");
            return;
        }
        var userName = args[0];
        var mode = args[1];

        var options = List.of("У вас сегодня будет удача в делах!", "Сегодня хороший день для саморазвития!");

        var randomizer = RandomizerFactory.getRandomizer(mode);
        int arrayIndex = randomizer.nextInt(0, options.size());

        System.out.println(userName + ", " + options.get(arrayIndex));
    }
}