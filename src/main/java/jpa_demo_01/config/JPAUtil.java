package jpa_demo_01.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for obtaining JPA EntityManagers.
 */
public class JPAUtil {

    // Name must match persistence-unit name in persistence.xml ("EmployeesPU")
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("EmployeesPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Optional: call this when shutting down the app (not strictly needed for this practicum)
    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
