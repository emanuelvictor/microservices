package com.emanuelvictor.api.functional.bertosimulators.infrastructure.browser;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrowserInstanceTests {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
        browserInstance = BrowserInstance.createNew("http://itau.com.br");
    }

    @Test
    void mustOpenBrowserWhenCreateAInstanceOfBrowserInstance() {
        Assertions.assertThat(browserInstance.getUrl()).isNotNull();
        Assertions.assertThat(browserInstance.getSessionId()).isNotNull();
    }

    @Test
    void mustOpenCreatedBrowserInstance() {
        final String sessionId = browserInstance.getSessionId();
        final String url = browserInstance.getUrl();
        final BrowserOptions browserOptions = BrowserOptions.create(sessionId, url);

        final BrowserInstance sameBrowserInstanceOpened = BrowserInstance.openCreated(browserOptions);

        Assertions.assertThat(sameBrowserInstanceOpened.getSessionId()).isEqualTo(sessionId);
        Assertions.assertThat(sameBrowserInstanceOpened.getUrl()).isEqualTo(url);
    }

    @Test
    void mustVerifyIfTheBrowserInstanceIsRunningAndMustReturnTrue() {

        final boolean isRunning = browserInstance.isRunning();

        Assertions.assertThat(isRunning).isTrue();
    }

    @Test
    void mustCloseBrowserInstanceAndNotThrowAnyException() {

        Assertions.assertThatCode(browserInstance::closeAllTheTabs).doesNotThrowAnyException();
    }

    @Test
    void mustOpenManyNewTabs() {
        browserInstance.openOnNewTab("https://parceiro.bv.com.br/ng-gpar-base-login/");
        browserInstance.openOnNewTab("https://veiculos.bancopan.com.br/login");
        browserInstance.openOnNewTab("https://veiculos.bancopan.com.br/login");
        browserInstance.openOnNewTab("https://www.credlineitau.com.br/simulator");
        browserInstance.openOnNewTab("https://omnimaisweb.omni.com.br/login");
        browserInstance.openOnNewTab("https://w0710.appmeucredito.com.br/");
        browserInstance.openOnNewTab("https://brpioneer.accenture.com/originacao-auto/login");

        var countOfTabs = browserInstance.getTabIds().size();

        Assertions.assertThat(countOfTabs).isEqualTo(browserInstance.getTabIds().size());
    }

    @Test
    void mustCloseAllTheTabs() {
    }

    @Test
    void mustCloseSpecificTab() {
    }

    @Test
    void tryToManipulateInputsOfOtherTab() {
    }

    @Test
    void mustVerifyIfTheBrowserInstanceIsRunningAndMustReturnFalseAfterItHasBeenClosed() {

        browserInstance.closeAllTheTabs();

        final boolean isRunning = browserInstance.isRunning();
        Assertions.assertThat(isRunning).isFalse();
    }

    @AfterEach
    public void afterEach() {
        try {
            final BrowserOptions browserOptions = BrowserOptions.create(browserInstance.getSessionId(), browserInstance.getUrl());
            BrowserInstance.openCreated(browserOptions).closeAllTheTabs();
        } catch (Exception ignore) {

        }
    }
}
