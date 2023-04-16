package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  private List<Employee> employees;

  @Captor
  private ArgumentCaptor<Employee> employeeArgumentCaptor;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    employees = Arrays.asList(new Employee(1L, "John Smith", 1000000, "Finance"),
        new Employee(2L, "Julius Villamayor", 2000000, "Engineering"));
  }

  @Test
  public void testGetEmployees() throws Exception {
    when(employeeService.retrieveEmployees()).thenReturn(employees);

    mockMvc.perform(get("/api/v1/employees")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(employees.size()))
        .andExpect(jsonPath("$[0].id").value(employees.get(0).getId()))
        .andExpect(jsonPath("$[0].name").value(employees.get(0).getName()))
        .andExpect(jsonPath("$[0].salary").value(employees.get(0).getSalary()))
        .andExpect(jsonPath("$[0].department").value(employees.get(0).getDepartment()))
        .andExpect(jsonPath("$[1].id").value(employees.get(1).getId()))
        .andExpect(jsonPath("$[1].name").value(employees.get(1).getName()))
        .andExpect(jsonPath("$[1].salary").value(employees.get(1).getSalary()))
        .andExpect(jsonPath("$[1].department").value(employees.get(1).getDepartment()));

    verify(employeeService, times(1)).retrieveEmployees();
  }

  @Test
  public void testGetEmployee() throws Exception {
    Employee employee = employees.get(0);
    Long employeeId = employee.getId();

    when(employeeService.getEmployee(employeeId)).thenReturn(employee);

    mockMvc.perform(get("/api/v1/employees/{employeeId}", employeeId)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(employee.getId()))
        .andExpect(jsonPath("$.name").value(employee.getName()))
        .andExpect(jsonPath("$.salary").value(employee.getSalary()))
        .andExpect(jsonPath("$.department").value(employee.getDepartment()));

    verify(employeeService, times(1)).getEmployee(employeeId);
  }

  @Test
  public void testSaveEmployee() throws Exception {
    Employee newEmployee =
        Employee.builder().id(1L).name("Jane Doe").salary(55000).department("HR").build();

    mockMvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newEmployee))).andExpect(status().isOk());

    verify(employeeService, times(1)).saveEmployee(employeeArgumentCaptor.capture());
    Employee capturedEmployee = employeeArgumentCaptor.getValue();

    assertEquals(newEmployee.getId(), capturedEmployee.getId());
    assertEquals(newEmployee.getName(), capturedEmployee.getName());
    assertEquals(newEmployee.getSalary(), capturedEmployee.getSalary());
    assertEquals(newEmployee.getDepartment(), capturedEmployee.getDepartment());
  }

  @Test
  public void testDeleteEmployee() throws Exception {
    Employee employee = employees.get(0);
    Long employeeIdToDelete = employee.getId();

    when(employeeService.getEmployee(employeeIdToDelete)).thenReturn(employee);

    mockMvc.perform(delete("/api/v1/employees/{employeeId}", employeeIdToDelete))
        .andExpect(status().isOk());

    verify(employeeService, times(1)).deleteEmployee(employeeIdToDelete);
  }

  @Test
  public void testUpdateEmployee() throws Exception {
    Employee existingEmployee = employees.get(0);
    Long employeeIdToUpdate = existingEmployee.getId();

    // TODO: Should not be able to update id
    Employee updatedEmployee =
        Employee.builder().name("Michael Jordan").salary(60000000).department("Ballin").build();

    when(employeeService.getEmployee(employeeIdToUpdate)).thenReturn(existingEmployee);

    mockMvc.perform(put("/api/v1/employees/{employeeId}", employeeIdToUpdate)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee))).andExpect(status().isOk());

    verify(employeeService, times(1)).updateEmployee(employeeArgumentCaptor.capture());
    Employee capturedEmployee = employeeArgumentCaptor.getValue();

    assertEquals(updatedEmployee.getId(), capturedEmployee.getId());
    assertEquals(updatedEmployee.getName(), capturedEmployee.getName());
    assertEquals(updatedEmployee.getSalary(), capturedEmployee.getSalary());
    assertEquals(updatedEmployee.getDepartment(), capturedEmployee.getDepartment());
  }
}
