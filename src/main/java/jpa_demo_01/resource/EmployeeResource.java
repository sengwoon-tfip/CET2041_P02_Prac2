package jpa_demo_01.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManager;
import jpa_demo_01.config.JPAUtil;
import jpa_demo_01.dao.EmployeeDAO;
import jpa_demo_01.entity.Employee;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    /**
     * Endpoint 2:
     * GET /api/employees/{empNo}
     * Returns Employee with dept_emp, dept_manager, salaries, titles.
     */
    @GET
    @Path("/{empNo}")
    public Response getEmployeeByNumber(@PathParam("empNo") int empNo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO(em);
            Employee employee = employeeDAO.findEmployee(empNo);

            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee with number " + empNo + " not found")
                        .build();
            }

            return Response.ok(employee).build();
        } finally {
            em.close();   //
        }
    }
}
