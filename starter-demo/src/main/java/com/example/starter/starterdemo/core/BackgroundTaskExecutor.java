package com.example.starter.starterdemo.core;

public interface BackgroundTaskExecutor {

    void schedule(String taskId, Runnable task);

}
