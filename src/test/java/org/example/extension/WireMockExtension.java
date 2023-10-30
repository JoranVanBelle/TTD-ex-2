package org.example.extension;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Rule;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockExtension implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback {

    public static WireMockServer wireMockServer;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println(wireMockServer.isRunning());
        if(!wireMockServer.isRunning()) {
            wireMockServer.start();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        wireMockServer.stop();
    }
}
