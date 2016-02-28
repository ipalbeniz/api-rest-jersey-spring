package com.demo.api.rest.resource;

import com.demo.api.rest.config.SpringApiTestingContextConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApiTestingContextConfig.class)
public abstract class ApiResourceTest {

    private static final String CONTEXT_CONFIG = "contextConfig";

    @Autowired
    private ApplicationContext applicationContext;

    protected static JerseyTest jerseyTest;

    @Before
    public void setUp() throws Exception {
        if (jerseyTest == null) {
            jerseyTest = new JerseyTest() {
                @Override
                protected Application configure() {
                    ResourceConfig config = new ResourceConfig(getTestedResourceClass());

                    // Use the same spring application context as the test
                    config.property(CONTEXT_CONFIG, applicationContext);

                    // Listen on the next available port
                    forceSet(TestProperties.CONTAINER_PORT, "0");

                    return config;
                }
            };

            jerseyTest.setUp();
        }
    }

    public abstract Class<? extends ApiResource> getTestedResourceClass();

    @AfterClass
    public static void tearDown() throws Exception {
        jerseyTest.tearDown();
    }
}
