package me.jsj.jojoldu.job.reader;

import me.jsj.jojoldu.TestConfig;
import me.jsj.jojoldu.domain.Product;
import me.jsj.jojoldu.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {"job.name=" + ProductPagingFailJobConfiguration.JOB_NAME})
@SpringBootTest(classes = TestConfig.class)
class ProductPagingFailJobConfigurationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    ProductRepository productRepository;

    @Test
    void 같은조건을읽고_업데이트를할때() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            productRepository.save(new Product(i, false));
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(productRepository.findAllBySoldOut().size()).isEqualTo(50);
    }
}