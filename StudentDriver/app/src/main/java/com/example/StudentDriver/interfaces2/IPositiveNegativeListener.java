package com.example.StudentDriver.interfaces2;


@FunctionalInterface

public interface IPositiveNegativeListener {
    void onPositive();

   default void onNegative(){}

    public static final class DefaultImpls {
        public static void onNegative(com.example.StudentDriver.interfaces2.IPositiveNegativeListener $this) {
        }
    }
}

