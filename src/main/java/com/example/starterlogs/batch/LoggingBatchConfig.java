package com.example.starterlogs.batch;

import com.example.starterlogs.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.repeat.RepeatStatus;

@Configuration
@EnableBatchProcessing
public class LoggingBatchConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoggingBatchConfig.class);

    @Bean
    public Job logJob(JobRepository jobRepository, Step logStep) {
        return new JobBuilder("logJob", jobRepository)
                .start(logStep)
                .build();
    }

    @Bean
    public Step logStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("logStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    executeLoggingStep();  // Calls a separate method for AOP logging
                    return RepeatStatus.FINISHED; // Return a valid status
                }, transactionManager)
                .build();
    }

    @Loggable
    private void executeLoggingStep() {
        logger.info("Executing batch log step...");
    }
}
