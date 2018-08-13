package org.suggs.cloudpoc.producer;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@RunWith(PactRunner.class)
@Provider("test_provider")
@PactFolder("pacts")
public class GreetingTest {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingTest.class);

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8901, "/greeting");

    private static ConfigurableWebApplicationContext application;

    @BeforeClass
    public static void start() {
        application = (ConfigurableWebApplicationContext) SpringApplication.run(Producer.class);
    }

    @State("test GET")
    public void toGetState() {
        LOG.info("--------------------------------");
    }

}