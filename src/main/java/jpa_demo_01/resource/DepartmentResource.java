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
     * Returns all departments
     */
    @GET
    public Response getAllDepartments() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            DepartmentDAO departmentDAO = new DepartmentDAO(em);
            List<Department> departments = departmentDAO.findAll();
            return Response.ok(departments).build();
        } finally {
            em.close();   // close EM
        }
    }
    @GET
    @Path("/{deptNo}")
    public Response findEmployeesByDept(@PathParam("deptNo") String deptNo,
                                        @QueryParam("pageNo") @DefaultValue("1")
                                        int pageNo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            DepartmentDAO departmentDAO = new DepartmentDAO(em);
            List<EmployeeInfoDTO> employeeDTOs =
                    departmentDAO.findEmployeesByDeptNo(deptNo, pageNo,
                            20);
            if (employeeDTOs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Department " + deptNo + " not found")
                        .build();
            }
            return Response.ok(employeeDTOs).build();

        } finally {
            em.close();   // close EM
        }
    }
}
