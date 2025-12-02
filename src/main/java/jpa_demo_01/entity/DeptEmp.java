package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite key class for DeptEmp entity.
 * Combination of employee number and department number.
 */
@Embeddable
class DeptEmpId implements Serializable {

    /**
     * Employee number associated with the record.
     */
    @Column(name = "emp_no")
    private Integer empNo;

    /**
     * Department number associated with the record.
     */
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * Default constructor
     */
    public DeptEmpId() {
    }

    /**
     * Constructs composite key using employee number and department number.
     *
     * @param empNo  the employee number
     * @param deptNo the department number
     */
    public DeptEmpId(Integer empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    /**
     * @return the employee number
     */
    public Integer getEmpNo() { return empNo; }

    /**
     * @return the department number
     */
    public String getDeptNo() { return deptNo; }

    /**
     * Compares this composite key to another
     *
     * @param o the other object
     * @return true if both keys match, false if does not match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptEmpId)) return false;
        DeptEmpId that = (DeptEmpId) o;
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    /**
     * Generates hash code based on the key fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}

/**
 * Entity representing relationship between Employee and Department entity,
 * includes employment date range.
 */
@Entity
@Table(name = "dept_emp")
@NamedQuery(
        name = "DeptEmp.findEmployeesByDept",
        query = "SELECT new jpa_demo_01.dto.EmployeeInfoDTO(e.empNo, " +
                "e.firstName, e.lastName, e.hireDate) FROM DeptEmp de JOIN " +
                "de.employee e WHERE de.department.deptNo = :deptNo ORDER BY " +
                "e.empNo"
)
public class DeptEmp {

    /**
     * Embedded composite primary key containing employee and department numbers.
     */
    @EmbeddedId
    private DeptEmpId id;

    /**
     * Reference to employee entity.
     * Mapped via empNo inside composite key.
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * Reference to department entity.
     * Mapped via deptNo inside composite key.
     */
    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    /**
     * Employment start date within the department.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * Employment end date within the department.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Default constructor
     */
    public DeptEmp() {
    }

    /**
     * @return composite primary key
     */
    public DeptEmpId getId() { return id; }

    /**
     * @return department entity
     */
    public Department getDepartment() { return department; }

    /**
     * @return start date
     */
    public LocalDate getFromDate() { return fromDate; }

    /**
     * @return end date
     */
    public LocalDate getToDate() { return toDate; }
}
