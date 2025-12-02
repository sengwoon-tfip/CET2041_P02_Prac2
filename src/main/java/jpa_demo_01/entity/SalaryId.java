package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite primary key for the Salary entity.
 * Consists of the employee number and the starting date of the salary record.
 */
@Embeddable
public class SalaryId implements Serializable {

    /**
     * Employee number associated with the salary record.
     */
    @Column(name = "emp_no")
    private int empNo;

    /**
     * Start date of salary record
     * Part of Salaries composite primary key.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * Default constructor
     */
    public SalaryId() {}

    /**
     * Constructs a SalaryId using employee number and starting date.
     *
     * @param empNo     employee id
     * @param fromDate  start date for the salary record
     */
    public SalaryId(int empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    /** @return the employee number */
    public int getEmpNo() { return empNo; }

    /** @param empNo sets the employee number */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /** @return the start date of salary record */
    public LocalDate getFromDate() { return fromDate; }

    /** @param fromDate sets start date of the salary record */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    /**
     * Compares this composite key to another
     *
     * @param o the other object
     * @return true if both keys match, false if does not match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryId)) return false;
        SalaryId that = (SalaryId) o;
        return empNo == that.empNo &&
                Objects.equals(fromDate, that.fromDate);
    }

    /**
     * Generates hash code based on the key fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}