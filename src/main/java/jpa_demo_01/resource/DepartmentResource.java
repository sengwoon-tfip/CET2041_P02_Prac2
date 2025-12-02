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

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    /**
     * Endpoint 1:
     * GET /api/departments
     * Returns list of all departments (entity, no DTO).
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
     * GET /api/departments/{deptNo}/employees?pageNo=1
     * Returns a page of EmployeeInfoDTO (20 per page).
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
                // Could mean invalid deptNo OR just no employees; for practicum,
                // we follow the pattern your instructor suggested.
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
