package jpa_demo_01.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


/**
 * Main Jersey configuration class.
 * Jersey will use this to discover REST resources.
 *
 * <p>This class extends {@link org.glassfish.jersey.server.ResourceConfig}
 * and defines the base API path as well as component scanning rules.
 *
 * <p>Responsibilities:
 * <ul>
 *     <li>Registers the base URI path for all REST endpoints ("/").</li>
 *     <li>Configures Jersey to scan the {@code jpa_demo_01.resource} package
 *         for classes annotated with {@code @Path}.</li>
 *     <li>Enables Jackson support for JSON serialization/deserialization.</li>
 * </ul>
 *
 * @see org.glassfish.jersey.server.ResourceConfig
 * @see org.glassfish.jersey.jackson.JacksonFeature
 */

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    /**
     * Creates a new {@code ApplicationConfig} and configures Jersey.
     *
     * <p>Configuration steps:
     * <ul>
     *     <li>Registers the {@code jpa_demo_01.resource} package for resource scanning.</li>
     *     <li>Enables Jackson JSON support via {@link JacksonFeature}.</li>
     * </ul>
     */
    public ApplicationConfig() {
        // Tell Jersey where to find @Path-annotated resource classes
        packages("jpa_demo_01.resource");

        // Enable Jackson JSON support
        register(JacksonFeature.class);
    }
}
