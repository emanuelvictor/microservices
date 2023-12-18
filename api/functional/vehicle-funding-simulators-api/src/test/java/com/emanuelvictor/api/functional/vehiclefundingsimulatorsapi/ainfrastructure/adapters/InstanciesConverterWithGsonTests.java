package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class InstanciesConverterWithGsonTests {

    @ParameterizedTest
    @MethodSource("getValuesToConvert")
    void createScaleneTriangles(final String name, final Integer code, final Object objectToConvert) {

        final ObjectThatBeConvertedFromObjectToConvert objectThatBeConvertedFromObjectToConvert = InstanciesConverterWithGson
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

    @Test
    void mustConvertRecursiveObjects() {
        final LeafObjectToConvert leafObjectToConvert = new LeafObjectToConvert("tangamandapil", 15L);
        final TrunkObjectToConvert trunkObjectToConvert = new TrunkObjectToConvert("xinforínpola", 15, leafObjectToConvert);
        final RootObjectToConvert rootObjectToConvert = new RootObjectToConvert("Name", 12, trunkObjectToConvert);

        final RootObjectConverted rootObjectConverted = InstanciesConverterWithGson
                .tryConvertInstances(RootObjectConverted.class, rootObjectToConvert);

        Assertions.assertThat(rootObjectConverted.code()).isEqualTo(rootObjectToConvert.code());
        Assertions.assertThat(rootObjectConverted.name()).isEqualTo(rootObjectToConvert.name());
        Assertions.assertThat(rootObjectConverted.trunkObject()).usingRecursiveComparison().isEqualTo(rootObjectToConvert.trunkObject());
        Assertions.assertThat(rootObjectConverted.trunkObject().leafObject()).usingRecursiveComparison().isEqualTo(rootObjectToConvert.trunkObject().leafObject());
    }

}
