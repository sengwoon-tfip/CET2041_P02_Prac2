package jpa_demo_01.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key class for DeptManager entity.
 * Represents the relationship between employee and department
 * in a managerial role.
 */
@Embeddable
public class DeptManagerId implements Serializable {

    /**
     * Employee number in the composite key.
     */
    @Column(name = "emp_no")
    private Integer empNo;

    /**
     * Department number in the composite key.
     */
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * Default constructor
     */
    public DeptManagerId() {
    }

    /**
     * Constructs a composite key using employee number and department number.
     *
     * @param empNo  Employee number
     * @param deptNo Department number
     */
    public DeptManagerId(Integer empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    /**
     * @return the employee number
     */
    public Integer getEmpNo() {
        return empNo;
    }

    /**
     * @return the department number
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * Compares this composite key to another
     *
     * @param o the other object
     * @return true if both keys match, false if does not match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptManagerId)) return false;
        DeptManagerId that = (DeptManagerId) o;
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

