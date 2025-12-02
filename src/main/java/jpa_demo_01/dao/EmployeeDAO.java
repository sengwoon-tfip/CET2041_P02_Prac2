package jpa_demo_01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa_demo_01.entity.Employee;
import jpa_demo_01.entity.Salaries;
import jpa_demo_01.entity.SalaryId;
import jpa_demo_01.entity.Titles;
import jpa_demo_01.entity.TitleId;

import java.time.LocalDate;

import java.util.List;

public class EmployeeDAO {

    protected final EntityManager em;

    public EmployeeDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Endpoint 2 helper:
     * Find a single employee by employee number (primary key).
     */
    public Employee findEmployee(int empNo) {
        return em.find(Employee.class, empNo);
    }

    /**
     * Endpoint 4 helper:
     * Perform promotion for a single employee.
     *
     * - Closes current title (to_date = 9999-01-01) if any
     * - Inserts a new title row
     * - If newSalary is not null:
     *      - closes current salary (to_date = 9999-01-01) if any
     *      - inserts a new salary row
     *
     * This method assumes the caller has already:
     *  - begun a transaction
     *  - verified that employee is not null
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
