package com.example.demowithtests.service.employeesCabinetsService;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinets;

public interface EmployeesCabinetsService {

    EmployeesCabinets addRelation(Employee employee, Cabinet cabinet);

    EmployeesCabinets getRelation(Integer employeeId, Integer cabinetId);

    void deleteRelation(Integer employeeId, Integer cabinetId);

}
