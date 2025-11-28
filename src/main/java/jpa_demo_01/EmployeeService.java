package jpa_demo_01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeService {
    protected EntityManager em;

    public EmployeeService(EntityManager em) {
        this.em = em;
    }

//    public Employee createEmployee(int id, String firstName, String lastName) {
//        Employee emp = new Employee(id);
//        emp.setFirstName(firstName);
//        emp.setLastName(lastName);
//        em.persist(emp);
//        return emp;
//    }

//    public void removeEmployee(int id) {
//        Employee emp = findEmployee(id);
//        if (emp != null) {
//            em.remove(emp);
//        }
//    }
//
//    public Employee raiseEmployeeSalary(int id, long raise) {
//        Employee emp = em.find(Employee.class, id);
//        if (emp != null) {
//            emp.setSalary(emp.getSalary() + raise);
//        }
//        return emp;
//    }

    public Employee findEmployee(int id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findAllEmployees() {
        TypedQuery<Employee> query = em.createQuery(
                  "SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
}
