package jpa_demo_01.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class TitleId implements Serializable {

    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "title")
    private String title;

    @Column(name = "from_date")
    private LocalDate fromDate;

    public TitleId() {}

    public TitleId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleId)) return false;
        TitleId that = (TitleId) o;
        return empNo == that.empNo && Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}

@Entity
@Table(name="titles")
public class Titles {
    @EmbeddedId
    private TitleId titleId;

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

    public LocalDate getTitleId() { return toDate; }
    public void setTitleId(LocalDate toDate) { this.toDate = toDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @Override
    public String toString() {
        return String.format("Id: %s, Title: %s, From date: %s, " +
                        "To date: %s",
                titleId.getEmpNo(), titleId.getTitle(), titleId.getFromDate(), this.toDate);
    }
}


