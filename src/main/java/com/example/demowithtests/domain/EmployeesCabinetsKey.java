package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesCabinetsKey implements Serializable {

    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "cabinet_id")
    private Integer cabinetId;

}
