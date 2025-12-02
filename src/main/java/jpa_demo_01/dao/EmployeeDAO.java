package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.dto.PromotionRequestDTO;
import jpa_demo_01.entity.Employee;

import java.util.List;

public class EmployeeDAO {
    protected EntityManager em;

    public EmployeeDAO(EntityManager em) {
        this.em = em;
    }

    public Employee findEmployee(int id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findAllEmployees() {
        TypedQuery<Employee> query = em.createQuery(
                  "SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
}
