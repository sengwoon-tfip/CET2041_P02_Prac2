package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.entity.Employee;
import jpa_demo_01.entity.Salaries;
import jpa_demo_01.entity.SalaryId;
import jpa_demo_01.entity.Titles;
import jpa_demo_01.entity.TitleId;

import java.time.LocalDate;

/**
 * Data Access Object (DAO) for {@link Employee} and related history tables
 * such as {@link Titles} and {@link Salaries}.
 *
 * <p>Provides helper methods used by REST resources to look up employees
 * and to apply promotion logic (title and salary history updates).</p>
 */
public class EmployeeDAO {

    /**
     * JPA {@link EntityManager} used for all database operations in this DAO.
     */
    protected final EntityManager em;

    /**
     * Constructs an {@code EmployeeDAO} with the given {@link EntityManager}.
     *
     * @param em the entity manager to use for persistence operations
     */
    public EmployeeDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Endpoint 2 helper:
     * Finds a single employee by employee number (primary key).
     *
     * @param empNo the employee number to search for
     * @return the matching {@link Employee}, or {@code null} if none found
     */
    public Employee findEmployee(int empNo) {
        return em.find(Employee.class, empNo);
    }

    /**
     * Endpoint 4 helper:
     * Performs a promotion for a single employee.
     *
     * <p>Operations performed:</p>
     * <ul>
     *     <li>Locate the current title row with {@code to_date = 9999-01-01} and
     *         close it by setting {@code to_date = effectiveFrom - 1 day}, if it exists.</li>
     *     <li>Insert a new title row effective from {@code effectiveFrom}.</li>
     *     <li>If {@code newSalary} is not {@code null}:
     *         <ul>
     *             <li>Locate the current salary row with {@code to_date = 9999-01-01} and
     *                 close it by setting {@code to_date = effectiveFrom - 1 day}, if it exists.</li>
     *             <li>Insert a new salary row effective from {@code effectiveFrom}.</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * <p><strong>Preconditions:</strong></p>
     * <ul>
     *     <li>The caller has already begun a transaction.</li>
     *     <li>{@code employee} is a managed entity and not {@code null}.</li>
     * </ul>
     *
     * @param employee      the employee being promoted (must be managed)
     * @param newTitle      the new job title to assign
     * @param newSalary     the new salary to set; if {@code null}, salary history is unchanged
     * @param effectiveFrom the date from which the promotion takes effect
     */
    public void promoteEmployee(Employee employee,
                                String newTitle,
                                Integer newSalary,
                                LocalDate effectiveFrom) {

        int empNo = employee.getEmpNo();
        LocalDate maxDate = LocalDate.of(9999, 1, 1);

        // --- Title history ---

        // Find current title: to_date = 9999-01-01
        TypedQuery<Titles> currentTitleQuery = em.createQuery(
                "SELECT t FROM Titles t " +
                        "WHERE t.titleId.empNo = :empNo AND t.toDate = :maxDate",
                Titles.class);
        currentTitleQuery.setParameter("empNo", empNo);
        currentTitleQuery.setParameter("maxDate", maxDate);

        Titles currentTitle = currentTitleQuery
                .getResultStream()
                .findFirst()
                .orElse(null);

        // Close current title if exists
        if (currentTitle != null) {
            currentTitle.setToDate(effectiveFrom.minusDays(1));
        }

        // Insert new title row
        TitleId newTitleId = new TitleId(
                empNo,
                newTitle,
                effectiveFrom
        );

        Titles newTitleEntity = new Titles();
        newTitleEntity.setTitleId(newTitleId);
        newTitleEntity.setEmployee(employee);
        newTitleEntity.setToDate(maxDate);

        em.persist(newTitleEntity);

        // --- Salary history (optional) ---

        if (newSalary != null) {
            // Find current salary: to_date = 9999-01-01
            TypedQuery<Salaries> currentSalaryQuery = em.createQuery(
                    "SELECT s FROM Salaries s " +
                            "WHERE s.salaryId.empNo = :empNo AND s.toDate = :maxDate",
                    Salaries.class);
            currentSalaryQuery.setParameter("empNo", empNo);
            currentSalaryQuery.setParameter("maxDate", maxDate);

            Salaries currentSalary = currentSalaryQuery
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (currentSalary != null) {
                currentSalary.setToDate(effectiveFrom.minusDays(1));
            }

            SalaryId newSalaryId = new SalaryId(
                    empNo,
                    effectiveFrom
            );

            Salaries newSalaryEntity = new Salaries();
            newSalaryEntity.setSalaryId(newSalaryId);
            newSalaryEntity.setEmployee(employee);
            newSalaryEntity.setSalary(newSalary);
            newSalaryEntity.setToDate(maxDate);

            em.persist(newSalaryEntity);
        }
    }
}
