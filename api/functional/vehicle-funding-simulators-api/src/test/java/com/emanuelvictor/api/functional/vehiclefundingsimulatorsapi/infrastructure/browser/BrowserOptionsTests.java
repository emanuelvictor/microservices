package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.browser;


import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowserOptionsTests {

    @Test
    void mustCreateAInstanceOfBrowserOptions() {
        final String sessionId = "sessionId";
        final String urlOfTheBrowserInstance = "urlOfTheBrowserInstance";

        final BrowserOptions browserOptions = BrowserOptions.create(sessionId, urlOfTheBrowserInstance);

        Assertions.assertThat(browserOptions.getSessionId()).isEqualTo(sessionId);
        Assertions.assertThat(browserOptions.getUrlOfTheBrowserInstance()).isEqualTo(urlOfTheBrowserInstance);
    }

    @ParameterizedTest
    @MethodSource("getInvalidParams")
    void cannotCreateBrowserOptionsWithInvalidParams(final String sessionId,
                                                     final String urlOfTheBrowserInstance,
                                                     final String errorMessage) {

        final Exception exception = assertThrows(BusinessException.class,
                () -> BrowserOptions.create(sessionId, urlOfTheBrowserInstance));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class).hasMessageContaining(errorMessage);
    }

    private static Stream<Arguments> getInvalidParams() {
        return Stream.of(
                Arguments.arguments(null, "url", BrowserOptions.INVALID_SESSION_ID_MESSAGE),
                Arguments.arguments("sessionId", null, BrowserOptions.INVALID_URL_OF_THE_BROWSER_INSTANCE)
        );
    }
}
