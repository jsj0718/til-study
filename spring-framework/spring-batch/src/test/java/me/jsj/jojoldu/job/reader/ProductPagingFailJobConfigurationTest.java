package me.jsj.jojoldu.job.reader;

import me.jsj.jojoldu.TestBatchConfig;
import me.jsj.jojoldu.domain.pay.Product;
import me.jsj.jojoldu.domain.pay.ProductRepository;
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
@SpringBootTest(classes = {ProductPagingFailJobConfiguration.class, TestBatchConfig.class})
@TestPropertySource(properties = {"job.name=" + ProductPagingFailJobConfiguration.JOB_NAME})
class ProductPagingFailJobConfigurationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    void clean() {
        productRepository.deleteAll();
    }

    @Test
    void 같은조건을읽고_업데이트를할때_getPage오버라이딩() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            productRepository.save(new Product(i, false));
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        /**
         * 페이징 문제 발생
         * 1. Cursor 사용
         * 2. Paging 사용 시 getPage() 수정
         */
        assertThat(productRepository.findAllBySoldOut().size()).isEqualTo(50);
    }
}