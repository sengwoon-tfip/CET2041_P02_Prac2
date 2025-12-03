package jpa_demo_01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Request-body DTO representing an employee promotion request.
 *
 * <p>This is the payload consumed by the
 * {@code POST /api/employees/promotions} endpoint.</p>
 */
public class PromotionRequestDTO {

    /**
     * Employee number of the employee being promoted.
     */
    private int empNo;

    /**
     * The new job title to assign to the employee.
     */
    private String newTitle;

    /**
     * The new salary to assign to the employee, if provided.
     *
     * <p>If {@code null}, only the title history is updated.</p>
     */
    private Integer newSalary;   // optional

    /**
     * The date from which the promotion takes effect.
     *
     * <p>Serialized to JSON as a string with pattern {@code yyyy-MM-dd}.</p>
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate effectiveFrom;

    /**
     * No-argument constructor required for JSON deserialization.
     */
    public PromotionRequestDTO() {
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
     * Returns the new job title.
     *
     * @return the new title
     */
    public String getNewTitle() {
        return newTitle;
    }

    /**
     * Sets the new job title.
     *
     * @param newTitle the new title to set
     */
    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    /**
     * Returns the new salary, if specified.
     *
     * @return the new salary, or {@code null} if none provided
     */
    public Integer getNewSalary() {
        return newSalary;
    }

    /**
     * Sets the new salary.
     *
     * @param newSalary the new salary to set; may be {@code null}
     */
    public void setNewSalary(Integer newSalary) {
        this.newSalary = newSalary;
    }

    /**
     * Returns the effective date of the promotion.
     *
     * @return the promotion effective date
     */
    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    /**
     * Sets the effective date of the promotion.
     *
     * @param effectiveFrom the date from which the promotion is effective
     */
    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}
