package it.tacticalpeople.tpdsserver.repository;

import it.tacticalpeople.tpdsserver.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
