package com.emanuelvictor.api.functional.assessment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 *
 */
class CombinationTests {


    @Test
    void combineTest() {
        int[] ints = new int[]{7, 5, 6, 8, 9};
        final Object[] combinations = new Object[ints.length];

        comb(ints);
    }

    int[] comb(final int[] ints) {
        for (int c = 0; c < ints.length; c++) {
            final int[] subArray = subArray(c, ints);
            final int localCombinations[][] = new int[subArray.length][];
            for (int i = 0; i < subArray.length; i++) {
                localCombinations[i] = new int[subArray.length - i];
                for (int j = subArray.length - i - 1; j >= 0; j--) {
                    localCombinations[i][j] = subArray[j];
                }
            }

            for (int i = 0; i < localCombinations.length; i++) {
                System.out.println(Arrays.toString(localCombinations[i]));
            }
        }


        return null;
    }

    /**
     *
     */
    @Test
    public void subArrayTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(subArray(new int[]{1, 3}, ints)).isEqualTo(new int[]{7, 6, 9});

        final int[] ints2 = new int[]{7, 5, 6, 8, 9, 14, 4, 3, 1};
        Assertions.assertThat(subArray(new int[]{1, 3, 4, 5}, ints2)).isEqualTo(new int[]{7, 6, 4, 3, 1});

        Assertions.assertThat(subArray(new int[]{0}, ints2)).isEqualTo(new int[]{5, 6, 8, 9, 14, 4, 3, 1});

        Assertions.assertThat(subArray(new int[]{0, 8}, ints2)).isEqualTo(new int[]{5, 6, 8, 9, 14, 4, 3});

        Assertions.assertThat(subArray(new int[]{0, 2, 3, 4, 5, 6, 8}, ints2)).isEqualTo(new int[]{5, 3});
    }

    int[] subArray(final int[] indexes, int[] array) {

        for (int i = 0; i < indexes.length; i++) {
            array = subArray(indexes[i] - i, array);
        }

        return array;
    }

    int[] subArray(final int index, int[] array) {

        final int[] arrayReturn = new int[array.length - 1];

        for (int i = 0; i < array.length; i++) {
            if (i < index)
                arrayReturn[i] = array[i];
            if (i > index)
                arrayReturn[i - 1] = array[i];
        }

        return arrayReturn;
    }

    int[] getRest(final int c, int[] array) {
        final int[] arrayReturn = new int[array.length - c];
//        for (int i = c; i < array.length; i++) {
//            arrayReturn[i - c] = array[i];
//        }
        if (array.length - c >= 0) System.arraycopy(array, c, arrayReturn, 0, array.length - c);
        return arrayReturn;
    }

    /**
     *
     */
    @Test
    void factorialTest() {
        Assertions.assertThat(fac(5)).isEqualTo(120);
        Assertions.assertThat(fac(2)).isEqualTo(2);
    }

    /**
     *
     */
    @Test
    void combinationTest() {
        Assertions.assertThat(comb(5, 5)).isEqualTo(31);
        Assertions.assertThat(comb(5)).isEqualTo(31);
        Assertions.assertThat(comb(1)).isEqualTo(1);
        Assertions.assertThatThrownBy(() -> comb(0)).isInstanceOf(ArithmeticException.class);
    }

    /**
     * @param number int
     * @return int
     */
    static int fac(final int number) {
        if (number > 1)
            return number * fac(number - 1);
        return number;
    }

    /**
     * @param p int
     * @return int
     */
    static int comb(final int p) {
        return comb(p, p);
    }

    /**
     * @param n int
     * @param p int
     * @return int
     */
    static int comb(final int n, final int p) {
        if (p == 1)
            return n;
        if (n == p)
            return 1 + comb(n, p - 1);
        int nFac = fac(n);
        int pFac = fac(p);
        int x = nFac / (pFac * fac(n - p));
        return x + comb(n, p - 1);
    }
}