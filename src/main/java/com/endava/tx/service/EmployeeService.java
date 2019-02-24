package com.endava.tx.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.endava.tx.domain.Employee;
import com.endava.tx.repository.SpringDataEmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final SpringDataEmployeeRepository employeeRepository;

    public void createEmployees(String... domainNames) {
        log.info("Creating employees: {}", Arrays.toString(domainNames));
        for (String domainName : domainNames) {
            employeeRepository.save(Employee.builder().domainName(domainName).build());
            log.info("Saved the employee: {}", domainName);
        }
    }

    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
