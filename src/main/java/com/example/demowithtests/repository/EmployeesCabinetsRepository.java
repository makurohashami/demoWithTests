package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.domain.EmployeesCabinetsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesCabinetsRepository extends JpaRepository<EmployeesCabinets, EmployeesCabinetsKey> {

    @Query("select count(*) from EmployeesCabinets where cabinet.id = :id and isActive = true")
    Integer selectCountOfActiveOccupiedPlacesInCabinetByHisId(Integer id);
}
