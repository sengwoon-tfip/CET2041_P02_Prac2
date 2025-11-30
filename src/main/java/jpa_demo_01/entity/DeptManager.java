package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class DeptManagerId implements Serializable {

    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "dept_no")
    private String deptNo;

    public DeptManagerId() {
    }

    public DeptManagerId(Integer empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptManagerId)) return false;
        DeptManagerId that = (DeptManagerId) o;
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}

@Entity
@Table(name = "dept_manager")
public class DeptManager {

    @EmbeddedId
    private DeptManagerId id;

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

    public DeptManager() {
    }

    public DeptManagerId getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}

