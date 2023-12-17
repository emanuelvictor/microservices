package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.browser;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.exceptions.BusinessException;

public class BrowserOptions {

    static final String INVALID_SESSION_ID_MESSAGE = "sessionId is required";
    static final String INVALID_URL_OF_THE_BROWSER_INSTANCE = "urlOfTheBrowserInstance is required";

    private final String sessionId;
    private final String urlOfTheBrowserInstance;

    private BrowserOptions(String sessionId, String urlOfTheBrowserInstance) {
        new BusinessException()
                .whenNullOrBlank(sessionId, INVALID_SESSION_ID_MESSAGE)
                .whenNullOrBlank(urlOfTheBrowserInstance, INVALID_URL_OF_THE_BROWSER_INSTANCE)
                .thenThrows();
        this.sessionId = sessionId;
        this.urlOfTheBrowserInstance = urlOfTheBrowserInstance;
    }

    static BrowserOptions create(String sessionId, String urlOfTheBrowserInstance) {
        return new BrowserOptions(sessionId, urlOfTheBrowserInstance);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUrlOfTheBrowserInstance() {
        return urlOfTheBrowserInstance;
    }
}
