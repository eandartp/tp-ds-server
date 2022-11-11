package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Employee;
import it.tacticalpeople.tpdsserver.dto.EmployeeDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EmployeeMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public Employee fromDtoToEmployee(EmployeeDto employeeDto)
    {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setAddress(employeeDto.getAddress());
        employee.setZip(employeeDto.getZip());
        employee.setCity(employeeDto.getCity());
        return employee;
    }

    public EmployeeDto fromEmployeeToDto(Employee employee)
    {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setZip(employeeDto.getZip());
        employeeDto.setCity(employeeDto.getCity());
        return employeeDto;
    }
}
