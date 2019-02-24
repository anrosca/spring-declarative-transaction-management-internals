package com.endava.tx.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public class ReportGenerationRequest {

    private ReportCode code;

    private ReportDestination destination;

    public Report toReport() {
        return Report.builder()
                .code(code)
                .destination(destination)
                .build();
    }
}
