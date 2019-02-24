package com.endava.tx.domain;

import java.util.Locale;

import lombok.Getter;

@Getter
public enum ReportLocale {

    ENGLISH("en-US"), ROMANIAN("ro-RO");

    private String languageTag;

    ReportLocale(final String languageTag) {
        this.languageTag = languageTag;
    }

    public Locale toLocale() {
        return Locale.forLanguageTag(languageTag);
    }
}
