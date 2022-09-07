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
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;


@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = ProductCursorFailJobConfiguration.JOB_NAME)
public class ProductCursorFailJobConfiguration {

    public static final String JOB_NAME = "productCursorFailJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job productCursorJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(productCursorStep())
                .build();
    }

    @Bean
    @JobScope
    public Step productCursorStep() {
        return stepBuilderFactory.get("productCursorStep")
                .<Product, Product>chunk(chunkSize)
                .reader(productCursorReader())
                .processor(productCursorProcessor())
                .writer(productItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Product> productCursorReader() {
        return new JpaCursorItemReaderBuilder<Product>()
                .name("productJpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM Product p WHERE p.soldOutStatus = false")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Product, Product> productCursorProcessor() {
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
