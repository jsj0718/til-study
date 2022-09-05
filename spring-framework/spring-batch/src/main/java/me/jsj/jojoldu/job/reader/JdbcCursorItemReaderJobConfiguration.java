package me.jsj.jojoldu.job.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.jojoldu.domain.Pay;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcCursorItemReaderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    @Bean
    public Job jdbcCursorItemReaderJob() {
        return jobBuilderFactory.get("jdbcCursorItemReaderJob")
                .start(jdbcCursorItemReaderStep())
                .build();
    }

    @Bean
    public Step jdbcCursorItemReaderStep() {
        return stepBuilderFactory.get("jdbcCursorItemReaderStep")
                //<Pay, Pay>에서 첫 번째 Pay는 Reader에서 반환할 타입이며, 두 번째 Pay는 Writer에 파라미터로 넘어갈 타입을 의미
                //chunkSize로 인자값을 넣은 경우 Reader와 Writer가 묶일 Chunk 트랜잭션 범위
                .<Pay, Pay>chunk(chunkSize)
                .reader(jdbcCursorItemReader())
                .writer(jdbcItemWriter())
                .build();
    }

    /**
     * ItemReader 장점
      - 데이터 Streaming 가능 (Chunk 단위로 수행되고 주기적으로 Commit 된다. -> 고성능 배치의 핵심)

     * CursorItemReader의 주의점
      - DB와의 SocketTimeout을 충분히 큰 값으로 설정해야 한다. (하나의 Connection으로 Batch 종료까지 이어지기 때문)
      - PagingItemReader의 경우 한 페이지마다 Connection이 맺어지고 끊기기 때문에 Timeout으로부터 안전하다.
     **/
    @Bean
    public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .fetchSize(chunkSize) //DB에서 한 번에 가져올 데이터 양 (paging은 limit, offset으로 분할 처리, cursor는 분할 처리 없이 진행)
                .dataSource(dataSource) //DB 접근을 위한 DataSource 할당
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class)) //쿼리 결과를 Java 인스턴스로 매핑하기 위한 Mapper
                .sql("SELECT id, amount, tx_name, tx_date_time, FROM pay") //Reader에서 사용할 쿼리문
                .name("jdbcCursorItemReader") //reader 이름 지정 (Spring Batch의 ExecutionContext에 저장될 이름
                .build();
    }

    @Bean
    public ItemWriter<Pay> jdbcItemWriter() {
        return items -> items.stream().forEach(pay -> log.info("Current Pay={}", pay));
    }
}
