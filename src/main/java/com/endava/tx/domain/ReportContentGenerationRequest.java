package com.endava.tx.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ReportContentGenerationRequest {

    private final Report report;

    private final ReportFormat format;

    private final ReportLocale locale;

    public ReportContent toReportContent() {
        return ReportContent.builder()
                .report(report)
                .format(format)
                .locale(locale.getLanguageTag())
                .build();
    }
}
