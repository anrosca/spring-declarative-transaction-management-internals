package com.endava.tx.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.endava.tx.domain.Employee;

public interface SpringDataEmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByDomainName(String domainName);
}
