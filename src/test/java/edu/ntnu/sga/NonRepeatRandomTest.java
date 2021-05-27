package edu.ntnu.sga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class NonRepeatRandomTest {
    @Test
    void nextIndex() {
        NonRepeatRandom nonRepeatRandom = new NonRepeatRandom(100);
        ArrayList<Integer> list = new ArrayList<>();
        int temp = nonRepeatRandom.nextIndex();
        while (temp != -1){
            list.add(temp);
            temp = nonRepeatRandom.nextIndex();
        }
        int[] array = list.stream().mapToInt(i->i).toArray();

        int[] testArr = new int[array.length];
        for (int i = 0; i < testArr.length; i++) {
            testArr[i] = i;
        }

        assert testArr[5] != array[5];
        Arrays.sort(array);
        assertArrayEquals(array, testArr);
    }

    @Test
    void reset() {
    }

    public static void main(String[] args) {
        NonRepeatRandom nonRepeatRandom = new NonRepeatRandom(100);
        ArrayList<Integer> arrayList = new ArrayList<>();
        int temp = nonRepeatRandom.nextIndex();
        int count = 1;
        while (temp != -1){
            arrayList.add(temp);
            if (count==100){
                System.out.println("Remaining: " + nonRepeatRandom.remaining());
                System.out.println("Filled: " + arrayList.size());
            } count++;
            temp = nonRepeatRandom.nextIndex();
        }
        System.out.println(arrayList.toString());
        System.out.println("Size: " + arrayList.size());
    }
}