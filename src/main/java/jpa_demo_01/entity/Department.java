package jpa_demo_01.entity;

import jakarta.persistence.*;

/**
 * Entity representing a department in the departments table.
 * Each department consists of department number and a department name.
 */
@Entity
@Table(name = "departments")
@NamedQuery(
        name = "Department.findAllDepartments",
        query = "SELECT d FROM Department d"
)
public class Department {
    /**
     * Department number associated with the record.
     */
    @Id
    @Column(name = "dept_no", columnDefinition = "char(4)")
    private String deptNo;

    /**
     * Department name associated with the record.
     */
    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    /**
     * Default constructor
     */
    public Department() {
    }

    /**
     * @return the department number
     */
    public String getDeptNo() { return deptNo; }

    /**
     * Sets department number.
     *
     * @param deptNo new department number
     */
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    /**
     * @return the department name
     */
    public String getDeptName() { return deptName; }

    /**
     * Sets department name.
     *
     * @param deptName new department name
     */
    public void setDeptName(String deptName) { this.deptName = deptName; }
}


