package com.codingshuttle.testApp.TestAppModule6.repositories;

import com.codingshuttle.testApp.TestAppModule6.TestContainerConfiguration;
import com.codingshuttle.testApp.TestAppModule6.entities.EmployeeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest   //this will load whole application and we can get all variable , in this case employeeRepository
@Import(TestContainerConfiguration.class)
@DataJpaTest        //this is to get repositories like : employeeRepository since we are not using SpringBootTest, this is test for dataJpa repository only
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeEntity employee;

    @BeforeEach
    void setup(){
        employee = EmployeeEntity.builder()
//                .id(1L)
                .name("Pankaj")
                .email("Pankaj@gmail.com")
                .salary(100L)
                .build();
    }

   /* @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmail() {
         //Arrange/Given
        employeeRepository.save(employee);

        //Act
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmail(employee.getEmail());

        //Assert
        assertThat(employeeEntityList).isNotNull();
        assertThat(employeeEntityList.get(0).getEmail()).isEqualTo(employee.getEmail());


    }

    @Test
    void testFindByEmail_whenEmailIsNotValid_thenReturnNullEmployeelist() {
        //Arrange/Given
        String email = "tempEmail@gmail.com";
        //if we use this email or the email of an employee
        //it will give empty because it transactional and after every transactional it is empted
        //and in this transactional with not inserted any employee
        //so database empty

        //Act
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmail(employee.getEmail());

        //Assert
        assertThat(employeeEntityList).isNotNull();
        assertThat(employeeEntityList.size()).isEqualTo(0);
    }*/
}