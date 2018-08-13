package org.suggs.cloudpoc.producer.pact;

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
import org.springframework.context.ConfigurableApplicationContext;
import org.suggs.cloudpoc.producer.Producer;

@RunWith(PactRunner.class)
@Provider("greeting_provider")
@PactFolder("pacts")
public class GreetingPactTest {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingPactTest.class);

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8901, "/");

    private static ConfigurableApplicationContext application;

    @BeforeClass
    public static void start() {
        application = SpringApplication.run(Producer.class);
    }

    @State("test GET")
    public void toGetState() {
        LOG.info("--------------------------------");
    }

}
