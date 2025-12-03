package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

/**
 * Represents a job title record for an employee, mapped to the {@code titles} table.
 * Each record is uniquely identified by a composite TitleId containing:
 * Employee number, title, start date of the title
 */
@Entity
@Table(name="titles")
@NamedQuery(
        name = "Titles.findCurrentTitle",
        query = "SELECT t FROM Titles t " +
                "WHERE t.titleId.empNo = :empNo AND t.toDate = :maxDate"
)
public class Titles {

    /**
     * Composite key containing employee number, title, and from-date.
     */
    @EmbeddedId
    private TitleId titleId;

    /**
     * The end date of the title. If set to a max-date value (9999-01-01),
     * it indicates employee's current title.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Reference to the associated employee. Mapped via emp_no in the composite key.
     * Marked with {@link JsonIgnore} to avoid circular serialization.
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

    /**
     * Default constructor.
     */
    public Titles() {}

    /**
     * Creates a new Titles entity.
     *
     * @param titleId composite key for this title record
     * @param toDate ending date of this title
     */
    public Titles(TitleId titleId, LocalDate toDate) {
        this.titleId = titleId;
        this.toDate = toDate;
    }

    /**
     * Returns the composite title id.
     *
     * @return the titleId
     */
    public TitleId getTitleId() { return titleId; }

    /**
     * Sets title id.
     *
     * @param titleId the new identifier
     */
    public void setTitleId(TitleId titleId) { this.titleId = titleId; }

    /**
     * Returns the end date of this title.
     *
     * @return the toDate
     */
    public LocalDate getToDate() { return toDate; }

    /**
     * Sets the end date of this title.
     *
     * @param toDate new end date
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * Returns the associated employee.
     *
     * @return the employee
     */
    public Employee getEmployee() { return employee; }

    /**
     * Sets the employee linked to this title record.
     *
     * @param employee the new employee reference
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * Returns a formatted string with key title information.
     *
     * @return formatted entity details
     */
    @Override
    public String toString() {
        return String.format("Id: %s, Title: %s, From date: %s, To date: %s",
                titleId.getEmpNo(), titleId.getTitle(), titleId.getFromDate(), this.toDate);
    }
}
