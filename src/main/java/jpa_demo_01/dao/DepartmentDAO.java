package jpa_demo_01.dao;

import jpa_demo_01.config.JPAUtil;
import jpa_demo_01.entity.Department;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DepartmentDAO {

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
