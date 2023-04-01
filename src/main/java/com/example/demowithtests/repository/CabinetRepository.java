package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetRepository extends JpaRepository<Cabinet, Integer> {
}
