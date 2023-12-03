package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.exceptions.RequiredFieldsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Brand.INVALID_BRAND_NAME_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrandTests {

    @Test
    void mustCreateAInstanceOfBrand() {
        final String brandName = "Wolks";

        final Brand brand = new BrandBuilder().name(brandName).build();

        Assertions.assertThat(brand.name()).isEqualTo(brandName);
    }

    @ParameterizedTest
    @MethodSource("getInvalidData")
    void cannotCreateAInstanceOfBrandWithInvalidData(final String brandName, final String message) {

        final Exception exception = assertThrows(RequiredFieldsException.class, () ->
                new BrandBuilder()
                        .name(brandName)
                        .build()
        );

        Assertions.assertThat(exception).isInstanceOf(RequiredFieldsException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> getInvalidData() {
        return Stream.of(
                Arguments.arguments(null, INVALID_BRAND_NAME_MESSAGE),
                Arguments.arguments("", INVALID_BRAND_NAME_MESSAGE)
        );
    }
}
