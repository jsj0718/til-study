package me.jsj.jojoldu.job.writer;

import me.jsj.jojoldu.TestBatchConfig;
import me.jsj.jojoldu.domain.pay.Pay;
import me.jsj.jojoldu.domain.pay.Pay2Repository;
import me.jsj.jojoldu.domain.pay.PayRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@EntityScan("me.jsj.jojoldu.domain")
@EnableJpaRepositories("me.jsj.jojoldu.domain")
@SpringBatchTest
@SpringBootTest(classes = {JpaItemWriterJobConfiguration.class, TestBatchConfig.class})
@TestPropertySource(properties = {"job.name=jpaItemWriterJob"})
class JpaItemWriterJobConfigurationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    PayRepository payRepository;

    @Autowired
    Pay2Repository pay2Repository;

    @AfterEach
    void clean() {
        payRepository.deleteAll();
        pay2Repository.deleteAll();
    }

    @Test
    void testWriter() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            payRepository.save(new Pay(i, "txName" + i, LocalDateTime.now()));
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(payRepository.findAll().size()).isEqualTo(50);
        assertThat(pay2Repository.findAll().size()).isEqualTo(50);
    }
}