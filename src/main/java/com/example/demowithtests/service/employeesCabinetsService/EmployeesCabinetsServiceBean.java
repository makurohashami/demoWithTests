package com.example.demowithtests.service.employeesCabinetsService;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.domain.EmployeesCabinetsKey;
import com.example.demowithtests.repository.EmployeesCabinetsRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeesCabinetsServiceBean implements EmployeesCabinetsService {

    private final EmployeesCabinetsRepository repository;

    @Override
    public EmployeesCabinets addRelation(Employee employee, Cabinet cabinet) {
        var relation = getRelation(employee.getId(), cabinet.getId());
        if (relation != null) {
            relation.setIsActive(Boolean.TRUE);
        } else {
            relation = new EmployeesCabinets();
            relation.setEmployee(employee);
            relation.setCabinet(cabinet);
        }
        return repository.save(relation);
    }

    @Override
    public EmployeesCabinets getRelation(Integer employeeId, Integer cabinetId) {
        return repository.findById(
                        EmployeesCabinetsKey
                                .builder()
                                .employeeId(employeeId)
                                .cabinetId(cabinetId)
                                .build()
                )
                .orElse(null);
    }

    @Override
    public void deleteRelation(Integer employeeId, Integer cabinetId) {
        var relation = getRelation(employeeId, cabinetId);
        if (relation == null)
            throw new ResourceNotFoundException();
        relation.setIsActive(Boolean.FALSE);
        repository.save(relation);
    }
}
