package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Требуется 2 параметра на входе.");
        }

        var options = new String[]{"У вас сегодня будет удача в делах!", "Сегодня хороший день для саморазвития!"};

        var randomizer = RandomizerFactory.getRandomizer(args[1]);
        int arrayIndex = randomizer.nextInt(0, options.length);

        System.out.println(args[0] + ", " + options[arrayIndex]);
    }
}