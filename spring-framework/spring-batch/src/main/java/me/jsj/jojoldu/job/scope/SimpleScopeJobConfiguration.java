package me.jsj.jojoldu.job.scope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.jojoldu.tasklet.SimpleJobTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleScopeJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final SimpleJobTasklet tasklet;

    @Bean
    public Job simpleScopeJob() {
        log.info(">>>>>> definition simpleScopeJob");
        return jobBuilderFactory.get("simpleScopeJob")
                .start(simpleScopeStep1())
                .next(simpleScopeStep2(null))
                .build();
    }

    public Step simpleScopeStep1() {
        log.info(">>>>>> definition simpleScopeStep1");
        return stepBuilderFactory.get("simpleScopeStep1")
                .tasklet(tasklet)
                .build();
    }

    @Bean
    @JobScope
    public Step simpleScopeStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>>> definition simpleScopeStep2");
        return stepBuilderFactory.get("simpleScopeStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> This is simpleScopeStep2");
                    log.info(">>>>>> requestDate: {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
