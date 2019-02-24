package com.endava.second;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.endava.tx.config.TransactionManagerConfiguration;
import com.endava.tx.domain.Employee;
import com.endava.tx.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
        public Employee create(Employee employee) {
            System.out.println("Creating a new employee");
            return employeeRepository.save(employee);
        }

        @PostConstruct
        public void init() {
            log.info("Creating a new employee in PostConstruct");
            create(Employee.builder()
                    .domainName("anrosca")
                    .firstName("Andrei")
                    .lastName("Rosca")
                    .email("Andrei.Rosca@endava.com")
                    .build());
        }

        // Solution 1
        @Autowired
        private PlatformTransactionManager transactionManager;

        // @PostConstruct
        // public void init() {
        //     TransactionTemplate template = new TransactionTemplate(transactionManager);
        //     template.execute(new TransactionCallbackWithoutResult() {
        //         @Override
        //         protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
        //             log.info("Creating a new employee in PostConstruct");
        //             create(Employee.builder()
        //                     .domainName("anrosca")
        //                     .firstName("Andrei")
        //                     .lastName("Rosca")
        //                     .email("Andrei.Rosca@endava.com")
        //                     .build());
        //         }
        //     });
        // }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @Transactional
        @interface PostProxyInitialized {

        }

        //Solution 2
        @PostProxyInitialized
        public void initPostProxy() {
            System.out.println("Creating a new employee in PostConstruct");
            create(Employee.builder()
                    .domainName("anrosca")
                    .firstName("Andrei")
                    .lastName("Rosca")
                    .email("Andrei.Rosca@endava.com")
                    .build());
        }
    }

    // @Component
    // @RequiredArgsConstructor
    // class PostProxyInitializedApplicationListener {
    //
    //     private final DefaultListableBeanFactory beanFactory;
    //
    //     @EventListener
    //     @SneakyThrows
    //     public void onApplicationEvent(final ContextRefreshedEvent event) {
    //         final String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
    //         for (String beanName : beanDefinitionNames) {
    //             BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
    //             String beanClassName = beanDefinition.getBeanClassName();
    //             if (beanClassName != null) {
    //                 Class<?> beanClass = Class.forName(beanClassName);
    //                 for (Method method : ReflectionUtils.getAllDeclaredMethods(beanClass)) {
    //                     if (method.isAnnotationPresent(InitMethodEmployeeService.PostProxyInitialized.class)) {
    //                         Object bean = beanFactory.getBean(beanName);
    //                         invokeInitMethod(bean, method);
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //
    //     @SneakyThrows
    //     private void invokeInitMethod(final Object bean, final Method method) {
    //         method.invoke(bean);
    //     }
    // }
}
