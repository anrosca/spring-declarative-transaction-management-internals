package com.endava.tx.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.tx.domain.ReportContent;
import com.endava.tx.domain.ReportContentGenerationRequest;
import com.endava.tx.repository.ReportContentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportContentGenerationService {

    private final ReportContentRepository reportContentRepository;

    @Transactional
    public void generate(final ReportContentGenerationRequest request) {
        log.info("Generating: {}", request);
        final ReportContent reportContent = request.toReportContent();
        reportContent.setContent(doGenerateReport());
        reportContentRepository.save(reportContent);
        sleep();
        log.info("Successfully generated: {}", request);
    }

    private byte[] doGenerateReport() {
        //very complex logic which generates the csv or pdf
        return new byte[] {1, 2, 3};
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
