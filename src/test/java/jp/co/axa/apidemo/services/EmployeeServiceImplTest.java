package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testRetrieveEmployees() {
    Employee employee1 = new Employee(1L, "John Doe", 1000, "IT");
    Employee employee2 = new Employee(2L, "Jane Smith", 2000, "Sales");
    List<Employee> employees = Arrays.asList(employee1, employee2);

    when(employeeRepository.findAll()).thenReturn(employees);

    List<Employee> result = employeeService.retrieveEmployees();

    assertEquals(2, result.size());
    assertEquals("John Doe", result.get(0).getName());
    assertEquals("IT", result.get(0).getDepartment());
    assertEquals(1000, (int) result.get(0).getSalary());
    assertEquals("Jane Smith", result.get(1).getName());
    assertEquals("Sales", result.get(1).getDepartment());
    assertEquals(2000, (int) result.get(1).getSalary());
  }

  @Test
  public void testGetEmployee() {
    Employee employee = new Employee(1L, "John Doe", 1000, "IT");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

    Employee result = employeeService.getEmployee(1L);

    assertEquals("John Doe", result.getName());
    assertEquals("IT", result.getDepartment());
    assertEquals(1000, (int) result.getSalary());
  }

  @Test
  public void testSaveEmployee() {
    Employee employee = new Employee(1L, "John Doe", 1000, "IT");

    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    employeeService.saveEmployee(employee);

    verify(employeeRepository, times(1)).save(employee);
  }

  @Test
  public void testDeleteEmployee() {
    Long employeeId = 1L;

    employeeService.deleteEmployee(employeeId);

    verify(employeeRepository, times(1)).deleteById(employeeId);
  }

  @Test
  public void testUpdateEmployee() {
    Employee employee = new Employee(1L, "John Doe", 1000, "IT");

    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    employeeService.updateEmployee(employee);

    verify(employeeRepository, times(1)).save(employee);
  }

}
