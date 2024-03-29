package me.jsj.jojoldu.job.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.jojoldu.domain.pay.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = ProductPagingFailJobConfiguration.JOB_NAME)
public class ProductPagingFailJobConfiguration {

    public static final String JOB_NAME = "productPagingFailJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job productPagingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(productPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step productPagingStep() {
        return stepBuilderFactory.get("productPagingStep")
                .<Product, Product>chunk(chunkSize)
                .reader(productPagingReaderV2())
                .processor(productPagingProcessor())
                .writer(productItemWriter())
                .build();
    }

    /**
     * Paging 처리 시 페이지 단위로 Query가 일어난다.
     * 이 때 Update가 처리되는 경우 계속해서 조회되는 데이터 양은 달라지는 반면 조회되는 0~10 / 11~20 / ... 으로 진행된다.
     * 따라서 모든 데이터가 Update 되지 않는 문제가 발생할 수 있다.
     */
    public JpaPagingItemReader<Product> productPagingReaderV1() {
        return new JpaPagingItemReaderBuilder<Product>()
                .name("productJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM Product p WHERE p.soldOutStatus = false")
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Product> productPagingReaderV2() {
        JpaPagingItemReader<Product> reader = new JpaPagingItemReader<>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT p FROM Product p WHERE p.soldOutStatus = false");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("productJpaPagingItemReader");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Product, Product> productPagingProcessor() {
        return item -> {
            item.soldOut();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Product> productItemWriter() {
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
