package com.example.demo.utils;

public class RandomNumber {
    public static int randNumber(int min, int max) {
        return (min + (int) (Math.random() * max));
    }
}
