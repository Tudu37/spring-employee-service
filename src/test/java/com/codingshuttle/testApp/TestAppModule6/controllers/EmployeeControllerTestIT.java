//package com.codingshuttle.testApp.TestAppModule6.controllers;
//
//import com.codingshuttle.testApp.TestAppModule6.TestContainerConfiguration;
//import com.codingshuttle.testApp.TestAppModule6.dto.EmployeeDto;
//import com.codingshuttle.testApp.TestAppModule6.entities.EmployeeEntity;
//import com.codingshuttle.testApp.TestAppModule6.repositories.EmployeeRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import(TestContainerConfiguration.class)
//class EmployeeControllerTestIT {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    private EmployeeEntity testEmployee;
//    private EmployeeDto testEmployeeDto;
//
//    @BeforeEach
//    void setup(){
//        testEmployee = EmployeeEntity.builder()
//                .id(1L)
//                .name("Pankaj")
//                .email("Pankaj@gmail.com")
//                .salary(100L)
//                .build();
//
//
//        testEmployeeDto = modelMapper.map(testEmployee,EmployeeDto.class);
//    }
//
//    @Test
//    void testGetEmployeeById(){
//        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
//        webTestClient.get()
//                .uri("/employees/{id}",savedEmployee.getId())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(EmployeeDto.class)
//                .isEqualTo(testEmployeeDto);
////                .value(employeeDto -> {
////                    assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
////                    assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
////                });
//    }
//
//
//}