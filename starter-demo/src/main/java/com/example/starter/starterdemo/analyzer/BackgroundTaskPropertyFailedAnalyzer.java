package com.example.starter.starterdemo.analyzer;

import com.example.starter.starterdemo.exception.BackgroundTaskPropertyException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import java.text.MessageFormat;

public class BackgroundTaskPropertyFailedAnalyzer extends AbstractFailureAnalyzer<BackgroundTaskPropertyException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BackgroundTaskPropertyException cause) {
        return new FailureAnalysis(MessageFormat.format("Exception when try to set property: {0}", cause.getMessage()),
                "set-application-properties", cause);
    }
}
