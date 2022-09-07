package me.jsj.jojoldu.job.processor;

import me.jsj.jojoldu.TestBatchConfig;
import me.jsj.jojoldu.domain.student.Teacher;
import me.jsj.jojoldu.domain.student.TeacherRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@EntityScan("me.jsj.jojoldu.domain")
@EnableJpaRepositories("me.jsj.jojoldu.domain")
@SpringBatchTest
@SpringBootTest(classes = {ProcessorFilterJobConfiguration.class, TestBatchConfig.class})
@TestPropertySource(properties = {"job.name=" + ProcessorFilterJobConfiguration.JOB_NAME})
class ProcessorFilterJobConfigurationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    TeacherRepository teacherRepository;

    @AfterEach
    void clean() {
        teacherRepository.deleteAll();
    }

    @Test
    void testProcessFilter_TeacherName() throws Exception {
        //given
        for (int i = 0; i < 50; i++) {
            Teacher teacher = Teacher.builder()
                    .name("teacher" + i)
                    .build();

            teacherRepository.save(teacher);
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(teacherRepository.findAll().size()).isEqualTo(50);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}