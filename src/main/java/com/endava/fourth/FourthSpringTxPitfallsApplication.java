package com.endava.fourth;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

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
import com.endava.tx.domain.ReportCode;
import com.endava.tx.domain.ReportContentGenerationRequest;
import com.endava.tx.domain.ReportDestination;
import com.endava.tx.domain.ReportFormat;
import com.endava.tx.domain.ReportGenerationRequest;
import com.endava.tx.domain.ReportLocale;
import com.endava.tx.repository.ReportRepository;
import com.endava.tx.service.ReportContentGenerationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableJpaRepositories(basePackages = "com.endava.tx.repository")
@ComponentScan(basePackages = "com.endava.tx.service")
@Import(TransactionManagerConfiguration.class)
public class FourthSpringTxPitfallsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FourthSpringTxPitfallsApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ReportGenerationService service) {
        return args -> {
            service.createReport(new ReportGenerationRequest(ReportCode.ELS001, ReportDestination.PUBLIC));
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

        // private final ApplicationEventPublisher eventPublisher;

        private final ReportContentGenerationService reportContentGenerationService;

        @Transactional(rollbackFor = FileNotFoundException.class)
        public void createReport(final ReportGenerationRequest reportGenerationRequest) throws Exception {
            throw new Exception();
            // final Report report = reportRepository.save(reportGenerationRequest.toReport());
            // for (ReportFormat format : ReportFormat.values()) {
            //     for (ReportLocale locale : ReportLocale.values()) {
            //         final ReportContentGenerationRequest request = new ReportContentGenerationRequest(report, format, locale);
            //         reportContentGenerationService.generate(request);
            //         // eventPublisher.publishEvent(request);
            //     }
            // }
            // sleep();
        }

        private void sleep() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // @Component
    // @AllArgsConstructor
    // public static class ReportGenerationEventListener {
    //
    //     private final ReportContentGenerationService reportContentGenerationService;
    //
    //     @EventListener
    //     @Async("reportsTaskExecutor")
    //     public void onReportContentGenerationRequest(ReportContentGenerationRequest request) {
    //         reportContentGenerationService.generate(request);
    //     }
    // }
}
