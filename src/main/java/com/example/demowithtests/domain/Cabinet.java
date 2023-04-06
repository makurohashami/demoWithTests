package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cabinets")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer capacity = 1;
    private Boolean isDeleted = Boolean.FALSE;
    @OneToMany(mappedBy = "cabinet")
    @ToString.Exclude
    private Set<EmployeesCabinets> employeesCabinets = new HashSet<>();

}
