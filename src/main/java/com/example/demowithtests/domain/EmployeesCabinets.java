package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users_cabinets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeesCabinets {

    @EmbeddedId
    private EmployeesCabinetsKey id = new EmployeesCabinetsKey();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @MapsId("employeeId")
    @ToString.Exclude
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "cabinet_id")
    @MapsId("cabinetId")
    @ToString.Exclude
    private Cabinet cabinet;

    private Boolean isActive = Boolean.TRUE;

}
