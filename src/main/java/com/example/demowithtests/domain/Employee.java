package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.Name;
import com.example.demowithtests.util.annotations.ToLowerCase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Name
    private String name;
    private String country;
    @ToLowerCase
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @OrderBy("country asc, id asc")
    private Set<Address> addresses = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isVisible = Boolean.TRUE;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @OrderBy("creationDate desc")
    private Set<Avatar> avatars = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pass_id", referencedColumnName = "id")
    private WorkPass workPass;
    @OneToMany(mappedBy = "employee")
    private Set<EmployeesCabinets> employeesCabinets = new HashSet<>();
}
