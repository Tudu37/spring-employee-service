package com.codingshuttle.testApp.TestAppModule6.repositories;

import com.codingshuttle.testApp.TestAppModule6.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByEmail(String email);
}
