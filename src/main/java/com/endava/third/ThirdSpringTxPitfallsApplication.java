package com.endava.third;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.endava.tx.config.TransactionManagerConfiguration;
import com.endava.tx.domain.Employee;
import com.endava.tx.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@Import(TransactionManagerConfiguration.class)
public class ThirdSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(BatchPersistEmployeeService service) {
        return (args) -> {
            service.batchCreate(Arrays.asList(Employee.builder()
                            .domainName("anrosca")
                            .firstName("Andrei")
                            .lastName("Rosca")
                            .email("Andrei.Rosca@endava.com")
                            .build(),
                    Employee.builder()
                            .firstName("eracila")
                            .lastName("Evghenii")
                            .lastName("Racila")
                            .email("Evghenii.Racila@endava.com")
                            .build()));
        };
    }

    @Service
    @Slf4j
    public static class BatchPersistEmployeeService {

        @Autowired
        private EmployeeRepository employeeRepository;

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public Employee create(Employee employee) {
            log.info("Creating a new employee");
            return employeeRepository.save(employee);
        }

        @Transactional
        public Collection<Employee> batchCreate(Collection<Employee> employees) {
            log.info("Batch creating users.");
            return employees.stream()
                    .map(this::create)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }
}





















































































//-javaagent:spring-agent-2.5.6.SEC03.jar -javaagent:aspectjweaver-1.9.2.jar