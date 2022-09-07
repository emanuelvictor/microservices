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

        System.out.println("Combinações possíveis: " + comb(ints.length));

        comb(ints);

//        for (int i = 0; i < localCombinations.length; i++) {
//            System.out.println(Arrays.toString(localCombinations[i]));
//        }
    }

    int[] comb(final int[] ints) {
        for (int i = 0; i < ints.length - 1; i++) { // run the counters
            for (int j = 1; j < ints.length - i; j++) {
                // print from the index I to index J + I
                System.out.print(ints[0]);
                System.out.println(" | " + Arrays.toString(subArrayInclude(ints, j, j + i)).replace("[", "").replace("]", ""));
            }
            System.out.println(" ------ ");
        }
        return null;
    }

    @Test
    void subArrayIncludeTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(subArrayInclude(ints, 1, 3)).isEqualTo(new int[]{5, 8});
        Assertions.assertThat(subArrayInclude(ints, 0, 0)).isEqualTo(new int[]{7});
    }

    int[] subArrayInclude(final int[] array, final int... indexes) {

        final int[] distinct = distinct(indexes);

        int[] arrayReturn = new int[distinct.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < distinct.length; j++) {
                if (i == distinct[j])
                    arrayReturn[j] = array[distinct[j]];
            }
        }

        return arrayReturn;
    }

    @Test
    void distinctTest() {
        final int[] ints = new int[]{7, 5, 7, 8, 9};
        Assertions.assertThat(distinct(ints)).isEqualTo(new int[]{7, 5, 8, 9});
    }

    public static int[] distinct(final int[] original) {

        int[] distinct = new int[original.length];
        int count = 0;
        for (int k : original) {
            boolean exists = false;
            for (int j = 0; j < count; j++) {
                if (distinct[j] == k) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                distinct[count++] = k;
            }
        }

        distinct = Arrays.copyOf(distinct, count);

        return distinct;

    }


    /**
     *
     */
    @Test
    public void subArrayExcludeTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(subArrayExclude(ints, 1, 3)).isEqualTo(new int[]{7, 6, 9});

        final int[] ints2 = new int[]{7, 5, 6, 8, 9, 14, 4, 3, 1};
        Assertions.assertThat(subArrayExclude(ints2, 1, 3, 4, 5)).isEqualTo(new int[]{7, 6, 4, 3, 1});

        Assertions.assertThat(subArrayExclude(ints2, 0)).isEqualTo(new int[]{5, 6, 8, 9, 14, 4, 3, 1});

        Assertions.assertThat(subArrayExclude(ints2, 0, 8)).isEqualTo(new int[]{5, 6, 8, 9, 14, 4, 3});

        Assertions.assertThat(subArrayExclude(ints2, 0, 2, 3, 4, 5, 6, 8)).isEqualTo(new int[]{5, 3});
    }

    int[] subArrayExclude(int[] array, final int... indexes) {

        for (int i = 0; i < indexes.length; i++) {
            array = subArrayExclude(array, indexes[i] - i);
        }

        return array;
    }

    int[] subArrayExclude(int[] array, final int index) {

        final int[] arrayReturn = new int[array.length - 1];

        for (int i = 0; i < array.length; i++) {
            if (i < index)
                arrayReturn[i] = array[i];
            if (i > index)
                arrayReturn[i - 1] = array[i];
        }

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

    @Test
    void halfPlusOneTest() {
        System.out.println(halfPlusOne(5));
        System.out.println(halfPlusOne(6));
    }

    int halfPlusOne(int num) {
        return num / 2 + 1;
    }
}