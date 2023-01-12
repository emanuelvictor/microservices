package com.emanuelvictor.api.functional.flowcreator.infrastructure.aid;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
public final class Utils {

    /**
     * Transforma uma array em uma lista, sem estourar exceção se o array for nullo ou vazio
     *
     * @param array T[]
     * @return <T> List<T>
     */
    public static <T> List<T> getListFromArray(final T[] array) {
        if (array == null || array.length == 0)
            return null;
        return Arrays.asList(array);
    }

    public static <T> T[] getArrayFromList(final List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        T[] itemsArray = (T[]) asdfa(list.get(0).getClass(), list.size());
        itemsArray = list.toArray(itemsArray);
        return itemsArray;
    }

    /**
     * TODO
     * @param clazz
     * @param capacity
     * @return
     * @param <E>
     */
    public static  <E> E[] asdfa(Class<E> clazz, int capacity) {
        return (E[]) Array.newInstance(clazz, capacity);
    }

    /**
     * A palavra nocache pode ser utilizada para requisições de imagem, assim o navegador não armazena o cache.
     *
     * @param schema {String}
     * @return {String}
     */
    public static String removeNoCache(final String schema) {
        if (schema.contains("?nocache"))
            return schema.replace(schema.substring(schema.indexOf("?nocache")), "");
        return schema;
    }

    /**
     * Remove as acentuações
     *
     * @param str {String}
     * @return {String}
     */
    public static String normalizeSymbolsAndAccents(final String str) {
        final String nfdNormalizedString = Normalizer.normalize(org.apache.commons.lang3.StringUtils.defaultString(str), Normalizer.Form.NFD);
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
