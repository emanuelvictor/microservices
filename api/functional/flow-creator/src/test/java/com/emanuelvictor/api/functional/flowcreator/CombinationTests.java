package com.emanuelvictor.api.functional.flowcreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 *
 */
class CombinationTests {

    /**
     *
     */
    @Test
    void factorialTest() {
        Assertions.assertThat(fac(5)).isEqualTo(120);
        Assertions.assertThat(fac(2)).isEqualTo(2);
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
     * @param p int
     * @return int
     */
    static long comb(final int p) {
        return comb(p, p);
    }

    /**
     * @param n int
     * @param p int
     * @return int
     */
    static long comb(final int n, final int p) {
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
    void generateTest() {
        Assertions.assertThat(generate(5).size()).isEqualTo(comb(5));
        Assertions.assertThat(generate(8).size()).isEqualTo(comb(8));
    }


    public Set<int[]> generate(int n) {
        final Set<int[]> elements = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            elements.addAll(generate(n, i));
        }
        return elements;
    }

    /**
     * @param countAlternatives {@link int}
     * @param groupAlternatives {@link int}
     * @return {@link Set<int>}
     */
    public Set<int[]> generate(final int countAlternatives, final int groupAlternatives) {
        final Set<int[]> combinations = new HashSet<>();
        final int[] combination = new int[groupAlternatives];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < groupAlternatives; i++) {
            combination[i] = i;
        }

        while (combination[groupAlternatives - 1] < countAlternatives) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = groupAlternatives - 1;
            while (t != 0 && combination[t] == countAlternatives - groupAlternatives + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < groupAlternatives; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

}