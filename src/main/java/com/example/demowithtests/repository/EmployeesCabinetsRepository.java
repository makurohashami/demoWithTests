package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.domain.EmployeesCabinetsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesCabinetsRepository extends JpaRepository<EmployeesCabinets, EmployeesCabinetsKey> {

}
