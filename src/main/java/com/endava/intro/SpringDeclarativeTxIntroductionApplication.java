package com.endava.intro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.endava.tx.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = "com.endava.tx")
@EnableJpaRepositories(basePackages = "com.endava.tx.repository")
@EntityScan(basePackages = "com.endava.tx.domain")
@Slf4j
public class SpringDeclarativeTxIntroductionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDeclarativeTxIntroductionApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(EmployeeService employeeService) {
        return args -> {
            employeeService.createEmployees("anrosca", "eracila");
            employeeService.findAll().forEach(employee -> log.info(employee.toString()));
        };
    }
}