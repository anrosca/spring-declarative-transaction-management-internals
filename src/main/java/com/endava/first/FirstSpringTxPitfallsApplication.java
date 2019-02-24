package com.endava.first;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.tx.config.TransactionManagerConfiguration;
import com.endava.tx.domain.Employee;
import com.endava.tx.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Import(TransactionManagerConfiguration.class)
@Slf4j
public class FirstSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(SimpleEmployeeService service) {
        return (args) -> {
            log.info("Batch creating employees");
            service.batchCreate(Arrays.asList(
                    Employee.builder().domainName("anrosca").build(),
                    Employee.builder().domainName("eracila").build()
            ));
            log.info("Finding all employees");
            final Stream<Employee> employees = service.findAll();
            log.info("All employees: {}", employees.collect(toList()));
        };
    }
}

@Service
@RequiredArgsConstructor
@Slf4j
class SimpleEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public Stream<Employee> findAll() {
        log.info("Finding all employees");
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false);
    }

    @Transactional
    void batchCreate(final List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }
}