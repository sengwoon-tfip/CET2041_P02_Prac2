package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name="titles")
@NamedQuery(
        name = "Titles.findCurrentTitle",
        query = "SELECT t FROM Titles t " +
                "WHERE t.titleId.empNo = :empNo AND t.toDate = :maxDate"
)
public class Titles {
    @EmbeddedId
    private TitleId titleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

    public Titles() {}
    public Titles(TitleId titleId, LocalDate toDate) {
        this.titleId = titleId;
        this.toDate = toDate;
    }

    public TitleId getTitleId() { return titleId; }
    public void setTitleId(TitleId titleId) { this.titleId = titleId; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    @Override
    public String toString() {
        return String.format("Id: %s, Title: %s, From date: %s, " +
                        "To date: %s",
                titleId.getEmpNo(), titleId.getTitle(), titleId.getFromDate(), this.toDate);
    }
}


