package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * {@link InstanciesConverter} cannot convert recursive objects. Use {@link InstanciesConverterWithGson}
 */
@Deprecated
final class InstanciesConverter {

    static <Out, In> Out tryConvertInstances(Class<Out> classOfOutputObject, In objectToConvert) {

        try {

            final var fieldsOfClassOfOutputObject = Arrays.stream(classOfOutputObject.getDeclaredFields())
                    .filter(field -> (Modifier.STATIC | Modifier.FINAL) != field.getModifiers())
                    .toList();
            final var mappingOfFieldsAndValues = new Object[fieldsOfClassOfOutputObject.size()];

            for (int i = 0; i < fieldsOfClassOfOutputObject.size(); i++) {
                final Class<?> classFromObjectToConvert = objectToConvert.getClass();
                for (final Field fieldFromObjectToConvert : classFromObjectToConvert.getDeclaredFields()) {
                    if (fieldFromObjectToConvert.getName().equals(fieldsOfClassOfOutputObject.get(i).getName())) {
                        fieldFromObjectToConvert.setAccessible(true);
                        final var value = fieldFromObjectToConvert.get(objectToConvert);
                        mappingOfFieldsAndValues[i] = value;
                    }
                }
            }

            final var inputNamesOfClassOfOutputObject = fieldsOfClassOfOutputObject.stream()
                    .map(Field::getName)
                    .toList();
            final Constructor<Out> constructorOfOutputObject =
                    (Constructor<Out>) getConstructorFromThisInputs(classOfOutputObject, inputNamesOfClassOfOutputObject)
                            .orElseThrow(); // TODO must except exclusive exception

            return constructorOfOutputObject.newInstance(mappingOfFieldsAndValues);
        } catch (final IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static Optional<Constructor<?>> getConstructorFromThisInputs(Class<?> classOfDomainObject, final String... inputNames) {
        return getConstructorFromThisInputs(classOfDomainObject, Arrays.asList(inputNames));
    }

    static Optional<Constructor<?>> getConstructorFromThisInputs(Class<?> classOfDomainObject, final List<String> inputNames) {
        return Arrays.stream(classOfDomainObject.getDeclaredConstructors())
                .filter(constructor ->
                        Arrays.stream(constructor.getParameters())
                                .map(Parameter::getName)
                                .allMatch(inputNames::contains)
                )
                .findAny();
    }
}
