package com.example.starterlogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class StarterLogsApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StarterLogsApplication.class);

    private final LoggingService loggingService;

    @Autowired
    public StarterLogsApplication(LoggingService loggingService){
        this.loggingService = loggingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(StarterLogsApplication.class, args);
    }

    @Override
    @Loggable
    public void run(String... args) throws Exception {
        logger.info("Starter Logs Batch Application is running...");
        loggingService.runJob();
    }
}
