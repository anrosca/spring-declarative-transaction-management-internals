package com.endava.fourth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.endava.tx.config.TransactionManagerConfiguration;
import com.endava.tx.domain.Employee;
import com.endava.tx.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@Import(TransactionManagerConfiguration.class)
// @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
// @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class FourthSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FourthSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(FooService fooService) {
        return args -> {
            fooService.foo();
        };
    }

    interface FooService {

        @Transactional
        void foo();
    }

    @Service
    @RequiredArgsConstructor
    class FooServiceImpl implements FooService {

        private final EmployeeRepository employeeRepository;

        @Override
        public void foo() {
            log.info("Creating a new employee");
            employeeRepository.save(Employee.builder()
                    .domainName("anrosca")
                    .email("Andrei.Rosca@endava.com")
                    .firstName("Andrei")
                    .lastName("Rosca")
                    .build());
        }
    }
}





















































































//-javaagent:spring-agent-2.5.6.SEC03.jar -javaagent:aspectjweaver-1.9.2.jar