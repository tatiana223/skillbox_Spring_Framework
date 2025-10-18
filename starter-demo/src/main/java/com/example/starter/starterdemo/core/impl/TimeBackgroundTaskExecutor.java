package com.example.starter.starterdemo.core.impl;

import com.example.starter.starterdemo.configuration.properties.BackgroundTaskProperties;
import com.example.starter.starterdemo.core.BackgroundTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${background-executor.default-executor}'.equals('time') and ${background-executor.enabled:true}")
@RequiredArgsConstructor
@Slf4j
public class TimeBackgroundTaskExecutor implements BackgroundTaskExecutor {

    private final BackgroundTaskProperties backgroundTaskProperties;

    private final ConcurrentTaskScheduler concurrentTaskScheduler;

    private final TaskStopper taskStopper;

    @Override
    public void schedule(String taskId, Runnable task) {
        log.debug("TimeBackgroundTaskExecutor: task with id {} preparing for schedule", taskId);

        var future = concurrentTaskScheduler.schedule(task,
                new PeriodicTrigger(backgroundTaskProperties.getTime().getInSecondsTime()));
        taskStopper.registryTask(taskId, future);
    }
}
