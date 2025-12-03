package jpa_demo_01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an employee entity mapped to the employees table.
 * Stores personal information, employment details, and relationships
 * with departments, managers, salaries, and titles.
 */
@Entity
@Table(name="employees")
public class Employee {

    /**
     * Enum representing gender values stored as strings.
     */
    private enum gender {
        M,
        F
    }

    /**
     * Unique employee identifier mapped to {@code emp_no}.
     */
    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    /**
     * Employee birthdate, formatted as yyyy-MM-dd.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="birth_date")
    private LocalDate birthDate;

    /**
     * Employee's first name.
     */
    @Column(name="first_name")
    private String firstName;

    /**
     * Employee's last name.
     */
    @Column(name="last_name")
    private String lastName;

    /**
     * Employee gender stored as a string representation of enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private gender gender;

    /**
     * Employee hire date, formatted as yyyy-MM-dd.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="hire_date")
    private LocalDate hireDate;

    /**
     * List of department-employee relationships for the employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DeptEmp> deptEmpList;

    /**
     * List of department-manager relationships for the employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DeptManager> deptManagerList;

    /**
     * List of salary records associated with the employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Salaries> salaries;

    /**
     * List of job titles held by the employee over time.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Titles> titles;

    /**
     * Default constructor.
     */
    public Employee() {}

    /**
     * Constructs an employee using given employee number.
     *
     * @param empNo the employee number
     */
    public Employee(int empNo) {
        this.empNo = empNo;
    }

    /**
     * @return the employee number
     */
    public Integer getEmpNo() { return empNo; }

    /**
     * @param empNo the employee number to set
     */
    public void setEmpNo(Integer empNo) { this.empNo = empNo; }

    /**
     * @return the employee birth date
     */
    public LocalDate getBirthDate() { return birthDate; }

    /**
     * @param birthDate the birth date to set
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    /**
     * @return the first name
     */
    public String getFirstName() { return firstName; }

    /**
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * @return the last name
     */
    public String getLastName() { return lastName; }

    /**
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * @return the employee gender
     */
    public gender getGender() { return gender; }

    /**
     * @param gender the gender to set
     */
    public void setGender(gender gender) { this.gender = gender; }

    /**
     * @return the hire date
     */
    public LocalDate getHireDate() { return hireDate; }

    /**
     * @param hireDate the hire date to set
     */
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    /**
     * @return list of department-employee relations
     */
    public List<DeptEmp> getDeptEmpList() { return deptEmpList; }

    /**
     * @return list of department-manager relations
     */
    public List<DeptManager> getDeptManagerList() { return deptManagerList; }

    /**
     * @return list of salary records
     */
    public List<Salaries> getSalaries() { return salaries; }

    /**
     * @return list of job titles held by the employee
     */
    public List<Titles> getTitles() { return titles; }

    /**
     * Returns a formatted string representation of the employee object.
     *
     * @return string containing employee details
     */
    @Override
    public String toString() {
        return String.format("Id: %s, Birth date: %s, First name: %s, " +
                        "Last name: %s, Gender: %s, Hire date: %s",
                this.empNo, this.birthDate, this.firstName, this.lastName,
                this.gender, this.hireDate);
    }
}

