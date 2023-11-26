package com.emanuelvictor.api.functional.bertosimulator.infrastructure.adapters.browser;

import com.emanuelvictor.api.functional.bertosimulator.infrastructure.exceptions.RequiredFieldsException;

public class BrowserOptions {
    private final String sessionId;
    private final String urlOfTheBrowserInstance;

    private BrowserOptions(String sessionId, String urlOfTheBrowserInstance) {
        new RequiredFieldsException()
                .whenNullOrBlank(sessionId, "sessionId is required")
                .whenNullOrBlank(urlOfTheBrowserInstance, "urlOfTheBrowserInstance is required")
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
