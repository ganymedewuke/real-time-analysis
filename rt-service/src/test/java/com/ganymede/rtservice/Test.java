package com.ganymede.rtservice;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
        String str = "I like; @@moon, like is not love!";
        String[] arr = str.split("\\W+");

        for (String s : arr) {
            System.out.println(s);
        }


    }
}
