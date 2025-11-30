package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class SalaryId implements Serializable {

    @Column(name = "emp_no")
    private int empNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    public SalaryId() {}
    public SalaryId(int empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryId)) return false;
        SalaryId that = (SalaryId) o;
        return empNo == that.empNo &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}

@Entity
@Table(name="salaries")
public class Salaries {
    @EmbeddedId
    private SalaryId salaryId;

    @Column(name = "salary")
    private int salary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

    public Salaries() {}

    public Salaries(SalaryId salaryId, int salary, LocalDate toDate) {
        this.salaryId = salaryId;
        this.salary = salary;
        this.toDate = toDate;
    }

    public SalaryId getSalaryId() { return salaryId; }
    public void setSalaryId(SalaryId salaryId) { this.salaryId = salaryId; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @Override
    public String toString() {
        return String.format("Id: %s, Salary: %s, From date: %s, " +
                        "To date: %s",
                salaryId.getEmpNo(), this.salary, salaryId.getFromDate(), this.toDate);
    }
}
