package jpa_demo_01.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main Jersey configuration class.
 * Jersey will use this to discover REST resources.
 */
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        // Tell Jersey where to find @Path-annotated resource classes
        packages("jpa_demo_01.resource");

        // Enable Jackson JSON support
        register(JacksonFeature.class);
    }
}
