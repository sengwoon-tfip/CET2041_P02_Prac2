package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.dto.EmployeeInfoDTO;
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

    public List<EmployeeInfoDTO> findEmployeesByDeptNo(String deptNo, int page,
                                                       int pageLimit) {
        int firstResult = (page - 1) * pageLimit;
        TypedQuery<EmployeeInfoDTO> query =
                em.createNamedQuery("DeptEmp.findEmployeesByDept",
                        EmployeeInfoDTO.class);

        query.setParameter("deptNo", deptNo);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageLimit);
        return query.getResultList();
    }
}
