package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

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

    public Salaries(SalaryId salaryId, Employee employee, int salary, LocalDate toDate) {
        this.salaryId = salaryId;
        this.employee = employee;
        this.salary = salary;
        this.toDate = toDate;
    }

    public SalaryId getSalaryId() { return salaryId; }
    public void setSalaryId(SalaryId salaryId) { this.salaryId = salaryId; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public int getSalary() { return salary; }
    public void setSalary(Integer salary) { this.salary = salary; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @Override
    public String toString() {
        return String.format("Id: %s, Salary: %s, From date: %s, " +
                        "To date: %s",
                salaryId.getEmpNo(), this.salary, salaryId.getFromDate(), this.toDate);
    }
}
