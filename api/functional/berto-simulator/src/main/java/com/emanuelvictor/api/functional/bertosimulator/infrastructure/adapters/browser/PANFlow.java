package com.emanuelvictor.api.functional.bertosimulator.infrastructure.adapters.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BVFlow {

    private BrowserInstance browserInstance;

    @BeforeEach
    public void setUp() {
        final BrowserOptions browserOptions = BrowserOptions.create(
                "406c63c053fe9bcf5154a66b6d33a517",
                "http://localhost:43275"
        );
        browserInstance = BrowserInstance.openCreated(browserOptions);
    }

    @Test
    void test() {
        browserInstance.openOnNewTab("https://parceiro.bv.com.br/spa-ppar-base-dealer-veiculo/aprovometro/simulacao/dados-do-cliente/");
    }
}
