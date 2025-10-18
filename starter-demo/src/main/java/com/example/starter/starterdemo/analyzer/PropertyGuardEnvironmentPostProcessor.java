package com.example.starter.starterdemo.analyzer;

import com.example.starter.starterdemo.exception.BackgroundTaskPropertyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class PropertyGuardEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String defaultExecutor = environment.getProperty("background-executor.default-executor");
        String cronExpression = environment.getProperty("background-executor.cron.expression");
        String timeValue = environment.getProperty("background-executor.time.in-seconds-time");
        String tasksSize = environment.getProperty("background-executor.tasksSize");
        boolean enabled = Boolean.parseBoolean(environment.getProperty("background-executor.enabled"));

        if (enabled) {
            check(defaultExecutor, cronExpression, timeValue, tasksSize);
        }
    }

    private void check(String defaultExecutor, String cronExpression, String timeValue, String tasksSize) {
        boolean isInvalidType = !StringUtils.hasText(defaultExecutor) || (!Objects.equals(defaultExecutor, "cron")
                && !Objects.equals(defaultExecutor, "time"));

        if (isInvalidType) {
            throw new BackgroundTaskPropertyException(
                    "Property background-executor.default-executor mast be set as cron or time! Current is: " + defaultExecutor
            );
        }
        if (Objects.equals(defaultExecutor, "cron") && !StringUtils.hasText(cronExpression)) {
            throw new BackgroundTaskPropertyException("Invalid cron expression for 'cron' type: " + cronExpression);
        }
        if (Objects.equals(defaultExecutor, "time") && !StringUtils.hasText(timeValue)) {
            throw new BackgroundTaskPropertyException("Invalid in-seconds-time for 'time' type: " + timeValue);
        }
        if (!StringUtils.hasText(tasksSize) || !tasksSize.matches("-?\\d+") || Integer.parseInt(tasksSize) <= 0) {
            throw new BackgroundTaskPropertyException("Invalid tasks size value. Must be int and not null! Current value: " + tasksSize);
        }
    }
}
