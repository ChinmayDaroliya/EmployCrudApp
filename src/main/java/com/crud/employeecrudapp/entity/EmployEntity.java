package com.crud.employeecrudapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class EmployEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //it make the id in incremental and series order in database
    private Long id;
    private String firstName;
    private String lastName;
    private String primaryNumber;
    private String alternateNumber;
    private String email;
    @ManyToOne(cascade = CascadeType.PERSIST) // this is used as every employ is associated with one department and department has its own entity
    @JoinColumn(name = "department_id") //to give name in database
    private DepartmentEntity department;
    private LocalDate dateOfJoining;

    private Double salary;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
}
