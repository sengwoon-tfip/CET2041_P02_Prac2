package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

/**
 * Entity representing the salaries table.
 * Stores salary record for employees using composite key.
 */
@Entity
@Table(name="salaries")
@NamedQuery(
        name = "Salaries.findCurrentSalary",
        query = "SELECT s FROM Salaries s " +
                "WHERE s.salaryId.empNo = :empNo AND s.toDate = :maxDate"
)
public class Salaries {

    /**
     * Composite primary key representing employee number and salary start date.
     */
    @EmbeddedId
    private SalaryId salaryId;

    /**
     * Salary amount during the period.
     */
    @Column(name = "salary")
    private int salary;

    /**
     * End date of the salary record.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Employee associated with this salary record.
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

    /**
     * Default constructor
     */
    public Salaries() {}

    /**
     * Constructs a Salaries record.
     *
     * @param salaryId  composite primary key
     * @param employee  employee associated with this salary
     * @param salary    salary amount
     * @param toDate    end date of salary period
     */
    public Salaries(SalaryId salaryId, Employee employee, int salary, LocalDate toDate) {
        this.salaryId = salaryId;
        this.employee = employee;
        this.salary = salary;
        this.toDate = toDate;
    }

    /**
     * @return the composite primary key for Salaries
     */
    public SalaryId getSalaryId() { return salaryId; }
    public void setSalaryId(SalaryId salaryId) { this.salaryId = salaryId; }

    /**
     * @return the employee associated with this salary record
     */
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * @return the salary amount
     */
    public int getSalary() { return salary; }
    public void setSalary(Integer salary) { this.salary = salary; }

    /**
     * @return the end date of the salary period
     */
    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * @return formatted string representation of the salary record
     */
    @Override
    public String toString() {
        return String.format("Id: %s, Salary: %s, From date: %s, " +
                        "To date: %s",
                salaryId.getEmpNo(), this.salary, salaryId.getFromDate(), this.toDate);
    }
}

