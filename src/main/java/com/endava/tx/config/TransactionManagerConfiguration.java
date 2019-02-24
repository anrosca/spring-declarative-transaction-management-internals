package com.endava.tx.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import com.endava.tx.manager.LoggingJpaTransactionManager;

@Configuration
@EntityScan(basePackages = "com.endava.tx.domain")
@ComponentScan(basePackages = "com.endava.tx.repository")
public class TransactionManagerConfiguration {

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new LoggingJpaTransactionManager();
    }
}
