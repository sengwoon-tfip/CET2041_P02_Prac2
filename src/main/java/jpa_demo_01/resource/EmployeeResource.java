package jpa_demo_01.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import jpa_demo_01.config.JPAUtil;
import jpa_demo_01.dao.EmployeeDAO;
import jpa_demo_01.dto.PromotionRequestDTO;
import jpa_demo_01.entity.Employee;

/**
 * REST resource providing endpoints related to employees.
 *
 * <p>Base path: {@code /api/employees}</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>{@code GET /api/employees/{empNo}} — fetch an employee by employee number.</li>
 *     <li>{@code POST /api/employees/promotions} — process an employee promotion.</li>
 * </ul>
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    /**
     * Endpoint 2:
     * <pre>
     * GET /api/employees/{empNo}
     * </pre>
     * Returns an employee with all relationships such as dept_emp, dept_manager,
     * salaries, and titles.
     *
     * @param empNo the employee number to look up (path parameter)
     * @return HTTP 200 (OK) with the {@link Employee} if found,
     *         or HTTP 404 (Not Found) if the employee does not exist
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
     * <pre>
     * POST /api/employees/promotions
     * </pre>
     * Consumes JSON describing a single employee promotion.
     *
     * <p>Example valid JSON body:</p>
     * <pre>{@code
     * {
     *   "empNo": 10001,
     *   "newTitle": "Senior Engineer 3",
     *   "newSalary": 100000,
     *   "effectiveFrom": "2022-01-01"
     * }
     * }</pre>
     *
     * @param request the promotion request payload containing employee number,
     *                new title, optional new salary, and effective date
     * @return HTTP 200 (OK) with a success message if promotion is applied,
     *         HTTP 400 (Bad Request) if validation fails,
     *         HTTP 404 (Not Found) if employee does not exist, or
     *         HTTP 500 (Internal Server Error) if an unexpected error occurs
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
