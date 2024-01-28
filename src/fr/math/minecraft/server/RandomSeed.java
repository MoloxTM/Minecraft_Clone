package fr.math.minecraft.server;

import java.util.Random;

public class RandomSeed extends Random {

    private static RandomSeed instance = null;

    private RandomSeed() {
        super(52);
    }

    public static RandomSeed getInstance()  {
        if(instance == null) {
            instance = new RandomSeed();
        }
        return instance;
    }
}
