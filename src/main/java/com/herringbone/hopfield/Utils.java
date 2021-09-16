package com.herringbone.hopfield;

import java.util.Arrays;

public class Utils {

    // Transform pattern [1, 0, 1, 0] => [1, -1, 1, -1]
    public static int[] transform(int[] pattern) {
        return Arrays.stream(pattern).map( s -> s == 0?-1:1).toArray();
    }

    public static Integer[] transform2(Integer[] pattern) {
        return (Integer[]) Arrays.stream(pattern).map( s -> s == 0?-1:1).toArray();
    }

    // Transform pattern [1, -1, 1, -1] => [1, 0, 1, 0]
    public static int[] retransform(int[] result) {
        return Arrays.stream(result).map( s -> s == -1?0:1).toArray();
    }
}
