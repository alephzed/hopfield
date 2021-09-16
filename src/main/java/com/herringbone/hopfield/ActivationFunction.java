package com.herringbone.hopfield;

public class ActivationFunction {

    // this is the step function
    public static int activation(int x) {
        return x <= 0?-1:1;
    }
}
