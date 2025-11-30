package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="employees")
public class Employee {
    private enum gender {
        M,
        F
    }
    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="hire_date")
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DeptEmp> deptEmpList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DeptManager> deptManagerList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Salaries> salaries;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Titles> titles;


    public Employee() {}
    public Employee(int empNo) {
        this.empNo = empNo;
    }

    public Integer getEmpNo() { return empNo; }
    public void setEmpNo(Integer empNo) { this.empNo = empNo; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public gender getGender() { return gender; }
    public void setGender(gender gender) { this.gender = gender; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public List<DeptEmp> getDeptEmpList() { return deptEmpList; }

    public List<DeptManager> getDeptManagerList() { return deptManagerList; }

    public List<Salaries> getSalaries() { return salaries; }

    public List<Titles> getTitles() { return titles; }

    @Override
    public String toString() {
        return String.format("Id: %s, Birth date: %s, First name: %s, " +
                        "Last name: %s, Gender: %s, Hire date: %s",
                this.empNo, this.birthDate, this.firstName, this.lastName,
                this.gender, this.hireDate);
    }
}
