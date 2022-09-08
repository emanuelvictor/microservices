package com.emanuelvictor.api.functional.assessment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 */
class CombinationTests {


    @Test
    void combineTest() {
        int[] alternatives = new int[]{5, 6, 7, 8, 9};
        int combs = comb(alternatives.length);

        System.out.println("Combinações possíveis: " + combs);

        Set<Combination> combinations = comb(alternatives);
        combinations.forEach(System.out::println);

        Assertions.assertThat(combinations.size()).isEqualTo(combs);

        alternatives = new int[]{7, 6, 7, 8, 8};
        combs = comb(alternatives.length);
        combinations = comb(alternatives);

        Assertions.assertThat(combinations.size()).isEqualTo(combs);

        alternatives = new int[]{7, 6, 1, 2, 3, 7, 21, 8, 88, 5, 16, 6, 71, 7, 81, 8, 9, 45};
        combs = comb(alternatives.length);

        Assertions.assertThat(combinations.size()).isEqualTo(combs);
    }

    static Set<Combination> comb(int[] ints) {
        final Set<Combination> combinations = new HashSet<>();

        for (int anInt : ints) combinations.add(new Combination(new int[]{anInt}));

        return comb(subArrayIncludeRange(ints, 0, 0), ints, ints, comb(ints.length), combinations);
    }


    static Set<Combination> comb(int[] pivots, int[] range, int[] originals, int totalOfCombinations, final Set<Combination> result) {

        for (int i = 0; i < range.length; i++)
            for (int j = 1; j < range.length - i; j++) {

                final String newPeer = Arrays.toString(subArrayIncludeRange(pivots, 0, pivots.length - 1)).replace("[", "").replace("]", "") + ", " + Arrays.toString(subArrayInclude(range, j, j + i)).replace("[", "").replace("]", "");

                final List<Integer> integers = Arrays.stream(newPeer.split(",")).map(s -> Integer.valueOf(s.trim())).sorted().collect(Collectors.toList());

                result.add(new Combination(integers.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()));
            }

        if (result.stream().distinct().count() >= totalOfCombinations) {
            return result;
        }

        if (pivots.length == originals.length)
            return comb(subArrayIncludeRange(rotate(originals), 0, 0), rotate(originals), rotate(originals), totalOfCombinations, result);
        return comb(subArrayIncludeRange(originals, 0, pivots.length), subArrayIncludeRange(range, 1, range.length - 1), originals, totalOfCombinations, result);
    }

    @Test
    void vaiTest() {
        final Combination combination1 = new Combination(new int[]{1, 2, 3});
        final Combination combination2 = new Combination(new int[]{3, 1, 2});

        Assertions.assertThat(combination1).isEqualTo(combination2);
    }

    public static class Combination {

        private final int[] choices;

        public Combination(int[] choices) {
            this.choices = choices;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Combination combination = (Combination) o;
            return Arrays.equals(Arrays.stream(this.choices).sorted().toArray(), Arrays.stream(combination.choices).sorted().toArray());
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(choices);
        }

        @Override
        public String toString() {
            return Arrays.toString(choices);
        }
    }

//    int[] comb(final int[] original) {
//
//        int[] ints = copy(original);
//
//        final Set<int[]> result = new HashSet<>();
//
//        int[] pivots = copy(ints);
//
//        for (int pivot = 0; pivot < pivots.length; pivot++) {
//            for (int i = pivot + 1; i < ints.length; i++) { // run the counters
//                System.out.println(Arrays.toString(ints));
//                for (int j = pivot + 1; j < ints.length - i; j++) {
//                    // print from the index I to index J + I
//                    System.out.print(ints[pivot]);
//                    System.out.println(" | " + Arrays.toString(subArrayInclude(ints, j, j + i)).replace("[", "").replace("]", ""));
//                }
//            }
//            ints = subArrayIncludeRange(ints, pivot + 1, pivots.length - 1);
//        }
//
////        for (int i = 0; i < ints.length; i++) { // run the counters
////            System.out.println(Arrays.toString(ints));
////            for (int j = 0; j < ints.length - i; j++) {
////                // print from the index I to index J + I
////                System.out.println(Arrays.toString(subArrayInclude(ints, j, j + i)).replace("[", "").replace("]", "") + "                     " + count++);
////                result.add(subArrayInclude(ints, j, j + i));
////            }
////        }
//
//        // Get distinct objects by key
//        System.out.println(result.stream().filter(distinctByKey(Arrays::toString)).count());
//
////            ints = subArrayExclude(ints, 0);
////        }
//
////        ints = rotate(original);
////
////        for (int i = 2; i < ints.length - 1; i++) { // run the counters
////
////            for (int j = 1; j < ints.length - i; j++) {
////                // print from the index I to index J + I
////                System.out.print(ints[0]);
//////                System.out.println(" | " + Arrays.toString(subArrayInclude(ints, j, j + i)).replace("[", "").replace("]", "") + " - " + count++);
////                System.out.println(" | " + Arrays.toString(subArrayIncludeRange(ints, j, j + i)).replace("[", "").replace("]", "") + " - " + count++);
////            }
////        }
//
//        return null;
//    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Test
    void copyTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(copy(ints)).isEqualTo(new int[]{7, 5, 6, 8, 9});
    }

    int[] copy(final int[] ints) {
        final int[] intsToReturn = new int[ints.length];
        intsToReturn[ints.length - 1] = ints[0];

        for (int i = 0; i < ints.length; i++) {
            intsToReturn[i] = ints[i];
        }

        return intsToReturn;
    }

    @Test
    void rotateTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(rotate(ints)).isEqualTo(new int[]{5, 6, 8, 9, 7});
    }

    static int[] rotate(final int[] ints) {
        final int[] intsToReturn = new int[ints.length];
        intsToReturn[ints.length - 1] = ints[0];

        for (int i = 1; i < ints.length; i++) {
            intsToReturn[i - 1] = ints[i];
        }

        return intsToReturn;
    }

    @Test
    void subArrayIncludeRangeTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(subArrayIncludeRange(ints, 1, 3)).isEqualTo(new int[]{5, 6, 8});
        Assertions.assertThat(subArrayIncludeRange(ints, 0, 0)).isEqualTo(new int[]{7});

        final int[] ints2 = new int[]{7, 5, 6, 8, 9, 16, 28, 39};
        Assertions.assertThat(subArrayIncludeRange(ints2, 1, 6)).isEqualTo(new int[]{5, 6, 8, 9, 16, 28});
        Assertions.assertThat(subArrayIncludeRange(ints2, 0, 0)).isEqualTo(new int[]{7});
    }

    static int[] subArrayIncludeRange(final int[] array, final int from, final int to) {
        final int[] range = new int[to + 1 - from];
        for (int i = 0; i < range.length; i++) {
            range[i] = from + i;
        }
        return subArrayInclude(array, range);
    }

    @Test
    void getRangeTest() {
        Assertions.assertThat(getRange(3, 18)).isEqualTo(new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18});
    }

    int[] getRange(final int from, final int to) {
        final int[] range = new int[to + 1 - from];
        for (int i = 0; i < range.length; i++) {
            range[i] = from + i;
        }
        return range;
    }

    @Test
    void subArrayIncludeTest() {
        final int[] ints = new int[]{7, 5, 6, 8, 9};
        Assertions.assertThat(subArrayInclude(ints, 1, 3)).isEqualTo(new int[]{5, 8});
        Assertions.assertThat(subArrayInclude(ints, 0, 0)).isEqualTo(new int[]{7});
    }

    static int[] subArrayInclude(final int[] array, final int... indexes) {

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