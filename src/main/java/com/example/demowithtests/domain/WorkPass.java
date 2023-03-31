package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "workpasses")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WorkPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel = AccessLevel.A1;
    private LocalDateTime expireDate;
    @OneToOne(mappedBy = "workPass")
    private Employee employee;
    private Boolean isDeleted = Boolean.FALSE;
    private Boolean isFree = Boolean.TRUE;
}
