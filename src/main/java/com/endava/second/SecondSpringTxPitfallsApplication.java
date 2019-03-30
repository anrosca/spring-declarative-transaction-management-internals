package com.endava.second;

import javax.annotation.PostConstruct;

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
public class SecondSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(InitMethodEmployeeService service) {
        return (args) -> {
        };
    }

    @Slf4j
    @RequiredArgsConstructor
    @Service
    public static class InitMethodEmployeeService {

        private final EmployeeRepository employeeRepository;

        @Transactional
        @PostConstruct
        public void init() {
            log.info("Creating a new employee in PostConstruct");
            System.out.println("Creating a new employee");
            employeeRepository.save(Employee.builder()
                    .domainName("anrosca")
                    .firstName("Andrei")
                    .lastName("Rosca")
                    .email("Andrei.Rosca@endava.com")
                    .build());
        }
    }
}
