package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.dto.EmployeeInfoDTO;
import jpa_demo_01.entity.Department;

import java.util.List;

/**
 * Data Access Object (DAO) for {@link Department} entities.
 *
 * <p>Provides methods to fetch all departments and to query employees
 * belonging to a department using a named query.</p>
 */
public class DepartmentDAO {

    /**
     * JPA {@link EntityManager} used to interact with the database.
     */
    protected EntityManager em;

    /**
     * Constructs a {@code DepartmentDAO} with the given {@link EntityManager}.
     *
     * @param em the entity manager to use for all persistence operations
     */
    public DepartmentDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Finds and returns all departments in the database.
     *
     * <p>This method uses the named query {@code Department.findAllDepartments}
     * and maps results into {@link Department} objects.</p>
     *
     * @return a {@link List} of all {@link Department} entities
     */
    public List<Department> findAll() {
        TypedQuery<Department> query =
                em.createNamedQuery("Department.findAllDepartments",
                        Department.class);
        return query.getResultList();
    }

    /**
     * Retrieves a paginated list of employees for the given department number.
     *
     * <p>This method uses the named query {@code DeptEmp.findEmployeesByDept}
     * and maps results into {@link EmployeeInfoDTO} objects.</p>
     *
     * @param deptNo    the department number (e.g. {@code "d001"})
     * @param page      the page number to retrieve
     * @param pageLimit the maximum number of records per page
     * @return a list of {@link EmployeeInfoDTO} for the requested department and page;
     *         may be empty if no matching records are found
     */
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
