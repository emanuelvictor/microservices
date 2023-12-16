package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.exceptions.BusinessException;
import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle.Brand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

        final Exception exception = assertThrows(BusinessException.class, () ->
                new BrandBuilder()
                        .name(brandName)
                        .build()
        );

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> getInvalidData() {
        return Stream.of(
                Arguments.arguments(null, Brand.INVALID_BRAND_NAME_MESSAGE),
                Arguments.arguments("", Brand.INVALID_BRAND_NAME_MESSAGE)
        );
    }
}
