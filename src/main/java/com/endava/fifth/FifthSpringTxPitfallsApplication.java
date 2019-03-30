package com.endava.fifth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.tx.config.TransactionManagerConfiguration;
import com.endava.tx.domain.Report;
import com.endava.tx.domain.ReportContentGenerationRequest;
import com.endava.tx.domain.ReportFormat;
import com.endava.tx.domain.ReportGenerationRequest;
import com.endava.tx.domain.ReportLocale;
import com.endava.tx.repository.ReportRepository;
import com.endava.tx.service.ReportContentGenerationService;

import static com.endava.tx.domain.ReportCode.ELS001;
import static com.endava.tx.domain.ReportDestination.PUBLIC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableJpaRepositories(basePackages = "com.endava.tx.repository")
@ComponentScan(basePackages = "com.endava.tx.service")
@Import(TransactionManagerConfiguration.class)
public class FifthSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FifthSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ReportGenerationService service) {
        return args -> {
            ReportGenerationRequest request = new ReportGenerationRequest(ELS001, PUBLIC);
            service.createReport(request);
        };
    }

    @Bean
    public ThreadPoolTaskExecutor reportsTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        return taskExecutor;
    }

    @RequiredArgsConstructor
    @Slf4j
    @Service
    public static class ReportGenerationService {

        private final ReportRepository reportRepository;

        private final ReportContentGenerationService reportContentGenerationService;

        @Transactional
        public void createReport(final ReportGenerationRequest reportGenerationRequest){
            final Report report = reportRepository.save(reportGenerationRequest.toReport());
            for (ReportFormat format : ReportFormat.values()) {
                for (ReportLocale locale : ReportLocale.values()) {
                    ReportContentGenerationRequest request =
                            new ReportContentGenerationRequest(report, format, locale);
                    reportContentGenerationService.generate(request);
                }
            }
        }
    }
}
