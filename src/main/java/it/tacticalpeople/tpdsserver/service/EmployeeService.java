package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.Employee;
import it.tacticalpeople.tpdsserver.dto.EmployeeDto;
import it.tacticalpeople.tpdsserver.mapper.EmployeeMapper;
import it.tacticalpeople.tpdsserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper mapper;

    /**
     * Save an Employee.
     * @param employeeDto to transform into entity and save.
     * @return the persisted entity
     */
    public Employee save(EmployeeDto employeeDto)
    {
        log.debug("Request to save Employee : {}", employeeDto);
        Employee employee = this.mapper.fromDtoToEmployee(employeeDto);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update an Employee
     * @param employeeDto to transform into entity to update partially.
     * @return the persisted entity
     */
    public Optional<Employee> partialUpdate(EmployeeDto employeeDto)
    {
        log.debug("Request to partially update Employee : {}", employeeDto);
        return employeeRepository
                .findById(employeeDto.getId())
                .map(existingEmployee -> {
                    if(employeeDto.getFirstName() != null)
                        existingEmployee.setFirstName(employeeDto.getFirstName());
                    if(employeeDto.getLastName() != null)
                        existingEmployee.setLastName(employeeDto.getLastName());
                    if(employeeDto.getAddress() != null)
                        existingEmployee.setAddress(employeeDto.getAddress());
                    if(employeeDto.getZip() != null)
                        existingEmployee.setZip(employeeDto.getZip());
                    if(employeeDto.getCity() != null)
                        existingEmployee.setCity(employeeDto.getCity());
                    return existingEmployee;
                }).map(employeeRepository::save);
    }

    /**
     * Get all the Employees
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll()
    {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll().stream()
                .map(employee -> mapper.fromEmployeeToDto(employee))
                .collect(Collectors.toList());
    }

    /**
     * Get one Employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public EmployeeDto findOne(Long id)
    {
        log.debug("Request to get Employee : {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        EmployeeDto employeeDto = null;
        if(employee.isPresent())
            employeeDto = this.mapper.fromEmployeeToDto(employee.get());
        return employeeDto;
    }


    /**
     * Delete the Employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id)
    {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

}
