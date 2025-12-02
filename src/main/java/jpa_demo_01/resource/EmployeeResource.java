package jpa_demo_01.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import jpa_demo_01.config.JPAUtil;
import jpa_demo_01.dao.EmployeeDAO;
import jpa_demo_01.dto.PromotionRequestDTO;
import jpa_demo_01.entity.Employee;
import jpa_demo_01.entity.Salaries;
import jpa_demo_01.entity.SalaryId;
import jpa_demo_01.entity.Titles;
import jpa_demo_01.entity.TitleId;

import java.time.LocalDate;

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

    /**
     * Endpoint 4:
     * POST /api/employees/promotions
     * Consumes JSON describing a single employee promotion.
     *
     * <pre>
     *     {@code
     *     E.g. Valid JSON body input:
     *      {
     *         "empNo": 10001,
     *         "newTitle": "Senior Engineer 3",
     *         "newSalary": 100000,
     *         "effectiveFrom": "2022-01-01"
     *      }
     *      }
     * </pre>
     *
     */
    @POST
    @Path("/promotions")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(PromotionRequestDTO request) {

        // Basic validation (HTTP concern)
        if (request == null ||
                request.getEmpNo() <= 0 ||
                request.getNewTitle() == null || request.getNewTitle().isBlank() ||
                request.getEffectiveFrom() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("empNo, newTitle and effectiveFrom are required")
                    .build();
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            EmployeeDAO employeeDAO = new EmployeeDAO(em);

            // 1. Ensure employee exists
            Employee employee = employeeDAO.findEmployee(request.getEmpNo());
            if (employee == null) {
                tx.rollback();
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee " + request.getEmpNo() + " not found")
                        .build();
            }

            // 2. Delegate promotion logic to DAO
            employeeDAO.promoteEmployee(
                    employee,
                    request.getNewTitle(),
                    request.getNewSalary(),
                    request.getEffectiveFrom()
            );

            tx.commit();

            // 3. Build HTTP response
            return Response.ok("Employee " + request.getEmpNo() +
                    " promoted to '" + request.getNewTitle() + "'" +
                    (request.getNewSalary() != null
                            ? " with new salary " + request.getNewSalary()
                            : "")
            ).build();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error performing promotion: " + e.getMessage())
                    .build();
        } finally {
            em.close();
        }
    }
}
