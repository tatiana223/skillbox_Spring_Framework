package org.example;

import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        String activeProfile = "init";

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(activeProfile);
        context.register(AppConfig.class);
        context.refresh();

        System.out.println("Active profile: " + activeProfile);
        context.getBean(ApplicationInterface.class).start();
        context.close();
    }
}