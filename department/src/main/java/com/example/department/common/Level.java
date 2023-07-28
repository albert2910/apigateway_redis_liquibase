package com.example.department.common;

public class Level {
    private static int x = 1;
    public static int returnLevel(int a, int b) {
        return a + b - x;
    }
}
