package jpa_demo_01.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class providing access to the application's singleton
 * {@link jakarta.persistence.EntityManagerFactory}.
 *
 * <p>This class creates a single EntityManagerFactory for the lifetime of the
 * application (for the persistence-unit "EmployeesPU") and exposes a method
 * to supply new {@link jakarta.persistence.EntityManager} instances to DAOs
 * and resources whenever they need database access.
 *
 * <p>Responsibilities:
 * <ul>
 *     <li>Centralizes creation of EntityManagerFactory.</li>
 *     <li>Provides EntityManager instances via {@link #getEntityManager()}.</li>
 *     <li>Optionally supports clean shutdown using {@link #close()}.</li>
 * </ul>
 *
 * @see jakarta.persistence.EntityManager
 * @see jakarta.persistence.EntityManagerFactory
 * @see jakarta.persistence.Persistence
 */

public class JPAUtil {

    /**
     * Singleton {@link EntityManagerFactory} used across the application.
     * <p>The name must match the persistence-unit name in {@code persistence.xml}
     * (here, {@code "EmployeesPU"}).</p>
     */
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("EmployeesPU");

    /**
     * Creates and returns a new {@link EntityManager} from the singleton factory.
     *
     * @return a new {@code EntityManager} instance; caller is responsible for closing it
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Closes the underlying {@link EntityManagerFactory}, if open.
     *
     * <p>For graceful shutdown in real applications.</p>
     */
    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
