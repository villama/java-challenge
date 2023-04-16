package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeServiceImpl is a service class that provides
 * operations related to Employee management.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Sets the employee repository to be used by this service.
     *
     * @param employeeRepository the employee repository
     */
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves a list of all employees.
     *
     * @return a list of employees
     */
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param employeeId the employee ID
     * @return the employee with the specified ID, or null if not found
     */
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    /**
     * Saves a new employee.
     *
     * @param employee the employee to save
     */
    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param employeeId the employee ID to delete
     */
    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Updates an existing employee with new information.
     *
     * @param employee the employee with updated information
     */
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
