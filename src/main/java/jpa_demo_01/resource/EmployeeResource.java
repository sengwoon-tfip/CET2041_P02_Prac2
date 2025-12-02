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
     */
    @POST
    @Path("/promotions")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(PromotionRequestDTO request) {

        // Basic validation
        if (request == null ||
                request.getEmpNo() <= 0 ||
                request.getNewTitle() == null || request.getNewTitle().isBlank() ||
                request.getEffectiveFrom() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("empNo, newTitle and effectiveFrom are required")
                    .build();
        }

        LocalDate effectiveFrom;
        try {
            effectiveFrom = request.getEffectiveFrom(); // yyyy-MM-dd
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("effectiveFrom must be in format yyyy-MM-dd")
                    .build();
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Ensure employee exists
            EmployeeDAO employeeDAO = new EmployeeDAO(em);
            Employee employee = employeeDAO.findEmployee(request.getEmpNo());

            if (employee == null) {
                tx.rollback();
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee " + request.getEmpNo() + " not found")
                        .build();
            }

            LocalDate maxDate = LocalDate.of(9999, 1, 1);

            // 2. Close off current title (to_date = '9999-01-01'), if any
            TypedQuery<Titles> currentTitleQuery = em.createQuery(
                    "SELECT t FROM Titles t " +
                            "WHERE t.titleId.empNo = :empNo AND t.toDate = :maxDate",
                    Titles.class);
            currentTitleQuery.setParameter("empNo", request.getEmpNo());
            currentTitleQuery.setParameter("maxDate", maxDate);

            Titles currentTitle = currentTitleQuery
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (currentTitle != null) {
                // End current title the day before the new one starts
                currentTitle.setToDate(effectiveFrom.minusDays(1));
            }

            // 3. Insert new title row
            TitleId newTitleId = new TitleId(
                    request.getEmpNo(),
                    request.getNewTitle(),
                    effectiveFrom
            );

            Titles newTitle = new Titles();
            newTitle.setTitleId(newTitleId);
            newTitle.setEmployee(employee);
            newTitle.setToDate(maxDate);  // still current until changed later

            em.persist(newTitle);

            // 4. If newSalary provided, update salary history too
            if (request.getNewSalary() != null) {

                TypedQuery<Salaries> currentSalaryQuery = em.createQuery(
                        "SELECT s FROM Salaries s " +
                                "WHERE s.salaryId.empNo = :empNo AND s.toDate = :maxDate",
                        Salaries.class);
                currentSalaryQuery.setParameter("empNo", request.getEmpNo());
                currentSalaryQuery.setParameter("maxDate", maxDate);

                Salaries currentSalary = currentSalaryQuery
                        .getResultStream()
                        .findFirst()
                        .orElse(null);

                if (currentSalary != null) {
                    currentSalary.setToDate(effectiveFrom.minusDays(1));
                }

                SalaryId newSalaryId = new SalaryId(
                        request.getEmpNo(),
                        effectiveFrom
                );

                Salaries newSalary = new Salaries();
                newSalary.setSalaryId(newSalaryId);
                newSalary.setEmployee(employee);
                newSalary.setSalary(request.getNewSalary());
                newSalary.setToDate(maxDate);

                em.persist(newSalary);
            }

            tx.commit();

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
