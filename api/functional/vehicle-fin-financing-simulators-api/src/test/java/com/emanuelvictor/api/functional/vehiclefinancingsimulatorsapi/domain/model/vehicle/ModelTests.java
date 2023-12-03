package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.exceptions.RequiredFieldsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Model.INVALID_BRAND_MESSAGE;
import static com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Model.INVALID_MODEL_NAME_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelTests {

    @Test
    void mustCreateAInstanceOfModel() {
        final String brandName = "Wolks";
        final Brand brand = new BrandBuilder().name(brandName).build();

        final Model model = new ModelBuilder().brand(brand).build();

        Assertions.assertThat(model.getBrandName()).isEqualTo(brandName);
    }

    @ParameterizedTest
    @MethodSource("getInvalidData")
    void cannotCreateAInstanceOfModelWithInvalidData(final String modelName, final Brand brand, final String message) {

        final Exception exception = assertThrows(RequiredFieldsException.class, () ->
                new ModelBuilder()
                        .name(modelName)
                        .brand(brand)
                        .build()
        );

        Assertions.assertThat(exception).isInstanceOf(RequiredFieldsException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> getInvalidData() {
        final Brand brand = new BrandBuilder().build();
        return Stream.of(
                Arguments.arguments(null, brand, INVALID_MODEL_NAME_MESSAGE),
                Arguments.arguments("", brand, INVALID_MODEL_NAME_MESSAGE),
                Arguments.arguments("", null, INVALID_BRAND_MESSAGE)
        );
    }
}
