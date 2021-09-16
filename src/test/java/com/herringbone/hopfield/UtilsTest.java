package com.herringbone.hopfield;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    public void testTransform() {
        int[] pattern = {0, 1, 1, 0, 0, 1, 1};
        int[] transformed = Utils.transform(pattern);
        assertArrayEquals(new int[]{-1, 1, 1, -1, -1, 1, 1}, transformed);
    }

    @Test
    void retransform() {
        int[] pattern = {-1, 1, 1, -1, -1, 1, 1};
        int[] retransformed = Utils.retransform(pattern);
        assertArrayEquals(new int[]{0, 1, 1, 0, 0, 1, 1}, retransformed);
    }
}
