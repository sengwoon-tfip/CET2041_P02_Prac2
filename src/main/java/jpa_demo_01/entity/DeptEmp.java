package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
class DeptEmpId implements Serializable {

    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "dept_no")
    private String deptNo;

    public DeptEmpId() {
    }

    public DeptEmpId(Integer empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    public Integer getEmpNo() { return empNo; }
    public String getDeptNo() { return deptNo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptEmpId)) return false;
        DeptEmpId that = (DeptEmpId) o;
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}

@Entity
@Table(name = "dept_emp")
@NamedQuery(
        name = "DeptEmp.findEmployeesByDept",
        query = "SELECT new jpa_demo_01.dto.EmployeeInfoDTO(e.empNo, " +
                "e.firstName, e.lastName, e.hireDate) FROM DeptEmp de JOIN " +
                "de.employee e WHERE de.department.deptNo = :deptNo ORDER BY e.empNo"
)

public class DeptEmp {

    @EmbeddedId
    private DeptEmpId id;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptEmp() {
    }

    public DeptEmpId getId() { return id; }
    public Department getDepartment() { return department; }
    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }
}
