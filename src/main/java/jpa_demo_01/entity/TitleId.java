package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite primary key for the {@link Titles} entity.
 * Represents a unique title record for an employee based on:
 * Employee number, job title and start date of the title
 *
 * Marked as {@code @Embeddable} so it can be embedded into the parent entity.
 * Implements {@link Serializable} as required for composite keys in JPA.
 */
@Embeddable
public class TitleId implements Serializable {

    /**
     * Employee number associated with this title record.
     */
    @Column(name = "emp_no")
    private int empNo;

    /**
     * Job title held by the employee.
     */
    @Column(name = "title")
    private String title;

    /**
     * Date when the employee started holding this title.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * Default constructor.
     */
    public TitleId() {}

    /**
     * Constructs a composite key using employee number, job title, and start date.
     *
     * @param empNo     the employee number
     * @param title     the job title
     * @param fromDate  the date this title began
     */
    public TitleId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    /**
     * @return the employee number
     */
    public int getEmpNo() { return empNo; }

    /**
     * @param empNo sets the employee number
     */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /**
     * @return the job title
     */
    public String getTitle() { return title; }

    /**
     * @param title sets the job title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * @return the date the title began
     */
    public LocalDate getFromDate() { return fromDate; }

    /**
     * @param fromDate sets the start date of the title
     */
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
        if (!(o instanceof TitleId)) return false;
        TitleId that = (TitleId) o;
        return empNo == that.empNo && Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    /**
     * Generates hash code based on the key fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}
