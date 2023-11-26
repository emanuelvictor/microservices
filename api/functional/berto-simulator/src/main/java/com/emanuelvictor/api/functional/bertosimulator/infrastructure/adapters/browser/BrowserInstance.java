package com.emanuelvictor.api.functional.bertosimulator.infrastructure.adapters.browser;

import com.emanuelvictor.api.functional.bertosimulator.infrastructure.chrome.ChromeDriverBuilder;
import jakarta.persistence.Transient;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.codec.w3c.W3CHttpCommandCodec;
import org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class BrowserInstance {

    @Transient
    private final RemoteWebDriver driver;

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public String getSessionId() {
        return driver.getSessionId().toString();
    }

    public String getUrl() {
        return ((HttpCommandExecutor) driver.getCommandExecutor()).getAddressOfRemoteServer().toString();
    }

    public Collection<String> getTabIds() {
        return driver.getWindowHandles();
    }

    private String getCurrentTabId() {
        return driver.getWindowHandle();
    }

    /**
     * @param browserOptions {@link BrowserOptions}
     */
    private BrowserInstance(BrowserOptions browserOptions) {
        try {
            driver = createDriverFromSession(browserOptions.getSessionId(), new URL(browserOptions.getUrlOfTheBrowserInstance()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new instance of Browser with random session id and instance URL.
     *
     * @param urlOfFirstTab {@link String} name of the browser instance
     * @return {@link BrowserInstance}
     */
//    public static BrowserInstance createNew(final String urlOfFirstTab) {
//        final var driver = new ChromeDriver();
//        final var executor = (HttpCommandExecutor) driver.getCommandExecutor();
//        final var urlOfTheBrowserInstance = executor.getAddressOfRemoteServer().toString();
//        final var sessionId = driver.getSessionId().toString();
//        driver.get(urlOfFirstTab);
//        final BrowserOptions browserOptions = BrowserOptions.create(sessionId, urlOfTheBrowserInstance);
//        return new BrowserInstance(browserOptions);
//    }
    public static BrowserInstance createNew(final String urlOfFirstTab) {
        final String driverHome = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
//        chromeOptions.addArguments("--disable-blink-features");
//        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

//        chrome_options.addArguments("--headless=new"); // when chromedriver > 108.x.x.x
//        chrome_options.addArguments("--headless=chrome"); // when chromedriver <= 108.x.x.x

        final ChromeDriver driver = new ChromeDriverBuilder().build(chromeOptions, driverHome);
        final var executor = (HttpCommandExecutor) driver.getCommandExecutor();
        final var urlOfTheBrowserInstance = executor.getAddressOfRemoteServer().toString();
        final var sessionId = driver.getSessionId().toString();

        final Map<String, Object> params = new HashMap<>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', { get: () => undefined })");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);
        driver.get(urlOfFirstTab);

        final BrowserOptions browserOptions = BrowserOptions.create(sessionId, urlOfTheBrowserInstance);
        return new BrowserInstance(browserOptions);
    }

    /**
     * @param browserOptions {@param BrowserOptions}
     * @return {@link BrowserInstance}
     */
    public static BrowserInstance openCreated(BrowserOptions browserOptions) {
        return new BrowserInstance(browserOptions);
    }

    public void closeAllTheTabs() {
        for (final String tabId : getTabIds()) {
            closeSpecificTab(tabId);
        }
    }

    public void closeSpecificTab(final String tabId) {
        driver.switchTo().window(tabId);
        driver.close();
    }

    boolean isRunning() {
        try {
            driver.getTitle();
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    public void openOnNewTab(final String url) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        final ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        driver.get(url);
    }

    private static RemoteWebDriver createDriverFromSession(final String sessionId, URL commandExecutor) {
        final CommandExecutor executor = new HttpCommandExecutor(commandExecutor) {

            @Override
            public Response execute(Command command) throws IOException {
                Response response;
                if (Objects.equals(command.getName(), "newSession")) {
                    response = new Response();
                    response.setSessionId(sessionId);
                    response.setStatus(0);
                    response.setValue(Collections.<String, String>emptyMap());

                    try {
                        Field commandCodec;
                        commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
                        commandCodec.setAccessible(true);
                        commandCodec.set(this, new W3CHttpCommandCodec());

                        Field responseCodec;
                        responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
                        responseCodec.setAccessible(true);
                        responseCodec.set(this, new W3CHttpResponseCodec());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } else {
                    return super.execute(command);
                }
                return response;
            }
        };

        return new RemoteWebDriver(executor, new DesiredCapabilities());
    }
}
