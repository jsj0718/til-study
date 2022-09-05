package me.jsj.jojoldu.job.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.jojoldu.domain.Product;
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

import static me.jsj.jojoldu.job.reader.ProductPagingFailJobConfiguration.JOB_NAME;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class ProductPagingFailJobConfiguration {

    public static final String JOB_NAME = "productPagingFailJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job productPagingJob() {
        return jobBuilderFactory.get("productPagingJob")
                .start(productPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step productPagingStep() {
        return stepBuilderFactory.get("productPagingStep")
                .<Product, Product>chunk(chunkSize)
                .reader(productPagingReader())
                .processor(productPagingProcessor())
                .writer(productItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Product> productPagingReader() {
        return new JpaPagingItemReaderBuilder<Product>()
                .name("productJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM Product p WHERE p.soldOutStatus = false")
                .pageSize(chunkSize)
                .build();
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
