package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.entity.Department;

import java.util.List;

public class DepartmentDAO {

    protected EntityManager em;

    public DepartmentDAO(EntityManager em) {
        this.em = em;
    }

    public List<Department> findAll() {
        TypedQuery<Department> query =
                em.createQuery("SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }
}
