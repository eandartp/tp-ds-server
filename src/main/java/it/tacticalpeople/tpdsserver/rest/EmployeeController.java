package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Employee;
import it.tacticalpeople.tpdsserver.dto.EmployeeDto;
import it.tacticalpeople.tpdsserver.repository.EmployeeRepository;
import it.tacticalpeople.tpdsserver.service.EmployeeService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private static final String ENTITY_NAME = "Employee";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<EmployeeDto>> findAllEmployee()
    {
        log.debug("EmployeeController - url: employeeAll - method: findAllEmployee");
        return new ResponseEntity<List<EmployeeDto>>(employeeService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<EmployeeDto> findByIdEmployee(@PathVariable Long id)
    {
        log.debug("EmployeeController - url: /employeeById/{id} - method: findByIdEmployee");
        EmployeeDto employeeDto = null;
        try
        {
            employeeDto = employeeService.findOne(id);
            return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeDto employeeDto) throws Exception
    {
        log.debug("EmployeeController - url: /employeeSave - method: saveEmployee");
        //add resource
        Employee newEmployee = employeeService.save(employeeDto);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).body(newEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id)
    {
        log.debug("EmployeeController - url: /employeeDelete/{id} - method: deleteEmployee");
        employeeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id", required=false) final Long id, @RequestBody EmployeeDto employeeDto)
            throws URISyntaxException, ResourceException
    {
        log.debug("EmployeeController: url: /employeeUpdate/{id} method: updateemployee");
        if (employeeDto.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, employeeDto.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!employeeRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Employee result = employeeService.save(employeeDto);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDto.getId().toString()))
                .body(result);
    }


}
