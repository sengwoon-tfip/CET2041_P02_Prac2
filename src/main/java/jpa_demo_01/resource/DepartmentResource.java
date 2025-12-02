package jpa_demo_01.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManager;
import jpa_demo_01.config.JPAUtil;
import jpa_demo_01.dao.DepartmentDAO;
import jpa_demo_01.dto.EmployeeInfoDTO;
import jpa_demo_01.entity.Department;

import java.util.List;


/**
 * REST resource exposing operations related to departments.
 *
 * <p>Base path: {@code /api/departments}</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>{@code GET /api/departments} — list all departments.</li>
 *     <li>{@code GET /api/departments/{deptNo}/employees?pageNo=1} —
 *         paginated list of employees in a department.</li>
 * </ul>
 */
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    /**
     * Endpoint 1:
     * <pre>
     * GET /api/departments
     * </pre>
     * Returns a list of all departments.
     *
     * @return HTTP 200 (OK) with a list of {@link Department},
     *         or an empty list if none are found
     */
    @GET
    public Response getAllDepartments() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            DepartmentDAO departmentDAO = new DepartmentDAO(em);
            List<Department> departments = departmentDAO.findAll();
            return Response.ok(departments).build();
        } finally {
            em.close();
        }
    }

    /**
     * Endpoint 3:
     * <pre>
     * GET /api/departments/{deptNo}/employees?pageNo=1
     * </pre>
     * Returns a page of {@link EmployeeInfoDTO} (20 per page) for a department.
     *
     * @param deptNo the department number (path parameter)
     * @param pageNo the page number to retrieve (query parameter, defaults to 1)
     * @return HTTP 200 (OK) with a list of employees, or
     *         HTTP 404 (Not Found) if the department is invalid or has no employees
     */
    @GET
    @Path("/{deptNo}")
    public Response getEmployeesByDepartment(
            @PathParam("deptNo") String deptNo,
            @QueryParam("pageNo") @DefaultValue("1") int pageNo
    ) {
        final int PAGE_SIZE = 20;

        EntityManager em = JPAUtil.getEntityManager();
        try {
            DepartmentDAO departmentDAO = new DepartmentDAO(em);
            List<EmployeeInfoDTO> employeeDTOs =
                    departmentDAO.findEmployeesByDeptNo(deptNo, pageNo, PAGE_SIZE);

            if (employeeDTOs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Department " + deptNo + " not found or has no employees.")
                        .build();
            }

            return Response.ok(employeeDTOs).build();
        } finally {
            em.close();
        }
    }
}
