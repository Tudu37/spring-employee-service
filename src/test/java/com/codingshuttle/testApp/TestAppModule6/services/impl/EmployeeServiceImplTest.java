package com.codingshuttle.testApp.TestAppModule6.services.impl;

import com.codingshuttle.testApp.TestAppModule6.TestContainerConfiguration;
import com.codingshuttle.testApp.TestAppModule6.dto.EmployeeDto;
import com.codingshuttle.testApp.TestAppModule6.entities.EmployeeEntity;
import com.codingshuttle.testApp.TestAppModule6.exceptions.ResourceNotFoundException;
import com.codingshuttle.testApp.TestAppModule6.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy    //spy because model mapper is third party and already a tested lib,so we dont need to test
    private  ModelMapper modelMapper;


    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setup(){
         mockEmployee = EmployeeEntity.builder()
                .id(1L)
                .name("Pankaj")
                .email("Pankaj@gmail.com")
                .salary(100L)
                .build();


        mockEmployeeDto = modelMapper.map(mockEmployee,EmployeeDto.class);
    }


    @Test
    public void testEmployeeById_WhenEmployeeIdPresent_ThenReturnEmployee(){
        Long id = mockEmployee.getId();


        //Give
        //here defining method and also the what output is through .thenReturn
        //method does not actually execute and go to Db and return , we define the output
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

        //Act
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        //Assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(mockEmployee.getId());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository,atMost(1)).findById(1L);
//        verify(employeeRepository,times(5)).findById(2L);

    }

    @Test
    public void testEmployeeById_WhenEmployeeIdNotPresent_ThenThrowException(){
        //Arrange
        Long id = mockEmployee.getId();
//        Long id = 1L;
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
//        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        //Assert
        assertThatThrownBy(()->employeeService.getEmployeeById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + id)
        ;
        verify(employeeRepository).findById(id);
    }

    @Test
    public  void  testCreateNewEmployee_whenValidEmployee_thenCreateEmployee(){

        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);

        //Act
        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

        //Assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(mockEmployeeDto.getId());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());  //this is dto return

        ArgumentCaptor<EmployeeEntity> employeeEntityArgumentCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository).save(employeeEntityArgumentCaptor.capture());

        EmployeeEntity capturedEmployee = employeeEntityArgumentCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());  //this entity argument pass

    }

    @Test
    public  void  testCreateNewEmployee_whenEmployeeExits_thenThrowException(){
        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of(mockEmployee));

        //act and assert
        assertThatThrownBy(()->employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: " + mockEmployeeDto.getEmail());
        verify(employeeRepository).findByEmail(anyString());
    }

    @Test
    public void testUpdateEmployee_whenDoesNotExists_thenThrowException(){
        Long id = 1L;
        //assign
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        //act and assert
        assertThatThrownBy(()->employeeService.updateEmployee(id,mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + id);

        verify(employeeRepository).findById(id);
    }

    @Test
    public void testUpdateEmployee_whenEmployeeDoesNotMatch_thenThrowException(){
        Long id = 1L;
        //assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setEmail("paul@gmail.com");
        //act and assert
        assertThatThrownBy(()->employeeService.updateEmployee(id,mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");

        verify(employeeRepository).findById(id);

    }

    @Test
    public void testUpdateEmployee_whenEmployeeValid_thenUpdateEmployee(){

        //assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);

        //act
        mockEmployeeDto.setName("Paul");
        mockEmployeeDto.setSalary(300L);
        EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto);

        //assert
        assertThat(updatedEmployeeDto).isNotNull();
        assertThat(updatedEmployeeDto).isEqualTo(mockEmployeeDto);
//        verify(employeeRepository.findById(anyLong()));
//        verify(employeeRepository.save(any(EmployeeEntity.class)));
    }

    @Test
    public void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException(){
        Long id = 1L;
        //assign
        when(employeeRepository.existsById(anyLong())).thenReturn(false);

        //act and assert
        assertThatThrownBy(()->employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + id);
//        verify(employeeRepository.existsById(anyLong()));
    }

    @Test
    public void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee(){
        Long id = 1L;

        //assign
        when(employeeRepository.existsById(anyLong())).thenReturn(true);

        assertThatCode(()->employeeService.deleteEmployee(id))
                .doesNotThrowAnyException();

        verify( employeeRepository).deleteById(id);



    }


}