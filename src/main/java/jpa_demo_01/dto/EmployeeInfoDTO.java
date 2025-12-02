package jpa_demo_01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing simplified information about an employee.
 *
 * <p>Used primarily for department-based pagination queries, where only a subset of
 * employee fields are needed.</p>
 */
public class EmployeeInfoDTO {

    /**
     * Unique employee number.
     */
    private int empNo;

    /**
     * Employee's first name.
     */
    private String firstName;

    /**
     * Employee's last name.
     */
    private String lastName;

    /**
     * Employee's hire date.
     *
     * <p>Serialized to JSON as a string with pattern {@code yyyy-MM-dd}.</p>
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * Constructs a fully initialized {@code EmployeeInfoDTO}.
     *
     * @param empNo     the employee number
     * @param firstName the employee's first name
     * @param lastName  the employee's last name
     * @param hireDate  the date the employee was hired
     */
    public EmployeeInfoDTO(int empNo, String firstName, String lastName, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    /**
     * No-argument constructor required by some frameworks and libraries
     * (e.g. Jackson) for object mapping.
     */
    public EmployeeInfoDTO() {
    }

    /**
     * Returns the employee number.
     *
     * @return the employee number
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * Sets the employee number.
     *
     * @param empNo the employee number to set
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * Returns the employee's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the employee's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the hire date.
     *
     * @return the hire date
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Sets the hire date.
     *
     * @param hireDate the hire date to set
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
