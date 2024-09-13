package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.stream.Stream;

@Deprecated
public class InstanciesConverterTests {

    @Test
    void getConstructorFromTheObjectThatBeConvertedFromObjectToConvertWhenAllTheParametersInputNamesExists() {

        final Optional<Constructor<?>> constructors = InstanciesConverter
                .getConstructorFromThisInputs(ObjectThatBeConvertedFromObjectToConvert.class, "name", "code", "description");

        Assertions.assertThat(constructors.isPresent()).isTrue();
    }

    @Test
    void cannotGetConstructorFromTheObjectThatBeConvertedFromObjectToConvertWhenAllTheParametersInputNamesNotExists() {

        final Optional<Constructor<?>> constructors = InstanciesConverter
                .getConstructorFromThisInputs(ObjectThatBeConvertedFromObjectToConvert.class, "code", "description");

        Assertions.assertThat(constructors.isPresent()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getValuesToConvert")
    void createScaleneTriangles(final String name, final Integer code, final Object objectToConvert) {

        final ObjectThatBeConvertedFromObjectToConvert objectThatBeConvertedFromObjectToConvert = InstanciesConverter
                .tryConvertInstances(ObjectThatBeConvertedFromObjectToConvert.class, objectToConvert);

        Assertions.assertThat(objectThatBeConvertedFromObjectToConvert.name()).isEqualTo(name);
        Assertions.assertThat(objectThatBeConvertedFromObjectToConvert.code()).isEqualTo(code);
    }

    private static Stream<Arguments> getValuesToConvert() {
        return Stream.of(
                Arguments.arguments("Name", null, new ObjectWithoutCodeButWithTangamandapil("Name", "Description")),
                Arguments.arguments("Name", 12, new ObjectWithCodeAndDescription("Name", 12, "Description")),
                Arguments.arguments("Name", 12, new ObjectThatBeConvertedFromObjectToConvert("Name", 12)),
                Arguments.arguments(null, null, new CompletlyDifferentObject("Xiphorimpola", 12, "Tangamandápil"))
        );
    }

}
