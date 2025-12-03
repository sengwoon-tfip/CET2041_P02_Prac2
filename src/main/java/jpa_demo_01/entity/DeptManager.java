package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity representing the `dept_manager` table.
 * Maps employees to the departments they manage, including date ranges.
 */
@Entity
@Table(name = "dept_manager")
public class DeptManager {

    /**
     * Composite primary key mapping employee and department IDs.
     */
    @EmbeddedId
    private DeptManagerId id;

    /**
     * Employee entity associated with the manager record.
     * Mapped via empNo portion in composite key.
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * Department entity associated with the manager record.
     * Mapped via deptNo in the composite key.
     */
    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    /**
     * The start date for the manager's term.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * The end date for the manager's term.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Default constructor
     */
    public DeptManager() {
    }

    /**
     * @return the composite primary key for this manager record
     */
    public DeptManagerId getId() {
        return id;
    }

    /**
     * @param id sets the manager id
     */
    public void setId(DeptManagerId id) {
        this.id = id;
    }

    /**
     * @return the employee for this manager record
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee sets the employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the department associated with this manager record
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department sets the manager department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return the start date of the manager
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate sets the manager promotion From date
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the end date of the manager
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * @param toDate sets the manager end date
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}


