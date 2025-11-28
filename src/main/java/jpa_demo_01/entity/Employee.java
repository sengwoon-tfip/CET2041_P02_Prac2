package jpa_demo_01.entity;

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
    @Column(name="emp_no")
    private int id;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private gender gender;

    @Column(name="hire_date")
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Titles> titles;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Salaries> salaries;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<DeptMgrs> deptMgrs;
//
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<EmpDepts> empDepts;

    public Employee() {}
    public Employee(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return String.format("Id: %s, Birth date: %s, First name: %s, " +
                        "Last name: %s, Gender: %s, Hire date: %s",
                this.id, this.birthDate, this.firstName, this.lastName,
                this.gender, this.hireDate);
    }
}
