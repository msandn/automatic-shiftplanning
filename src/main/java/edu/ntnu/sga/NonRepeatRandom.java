package edu.ntnu.sga;

/**
 * A class that that creates an array of indexes and returns a random one with each call without repetition untill there are no more indexes
 * Follows a modified Fisher–Yates shuffle algorithm
 */
public class NonRepeatRandom {
    private int[] indexes;
    private int max;

    /**
     * Constructor
     * @param length Length of the index array
     */
    NonRepeatRandom(int length){
        indexes = new int[length];
        for (int i = 0; i < length; i++) {
            indexes[i] = i;
        }
        reset();
    }

    /**
     * Returns index and sets the index used to unusable
     * @return Returns the next unused index.
     */
    public int nextIndex(){
        if (max<0){
             return -1;
        }
        int rand = 0;
        if (max != 0){
            rand = Random.nextInt(max);;
        }
        int temp = indexes[max];
        indexes[max] = indexes[rand];
        indexes[rand] = temp;
        max--;
        return indexes[max+1];
    }

    /**
     * @return Number of indexes that has not been picked
     */
    public int remaining(){
        //+1 for index
        return max+1;
    }

    /**
     * Resets the class, all indexes are back in the pool
     */
    public void reset(){
        max = indexes.length-1;
    }
}
