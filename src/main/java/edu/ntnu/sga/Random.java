package edu.ntnu.sga;

import java.util.SplittableRandom;

/**
 * Singleton wrapper class that is responsible for generating all random
 */
public class Random {
    public static SplittableRandom random;

    private Random(){
        random = new SplittableRandom();
    }

    public static int nextInt(int bound){
        return nextInt(0, bound);
    }

    public static int nextInt(int origin, int bound){
        if(random == null){
            random = new SplittableRandom();
        }
        return random.nextInt(origin, bound);
    }

    public static double nextDouble(double bound){
        return nextDouble(0, bound);
    }

    public static double nextDouble(double origin, double bound){
        if(random == null){
            random = new SplittableRandom();
        }
        return random.nextDouble(origin, bound);
    }
}
