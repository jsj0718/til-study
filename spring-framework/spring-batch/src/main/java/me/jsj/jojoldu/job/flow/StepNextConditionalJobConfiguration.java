package me.jsj.jojoldu.job.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get("stepNextConditionalJob")
                .start(conditionalJobStep1())
                    .on("FAILED") //FAILED(ExitStatus)인 경우
                    .to(conditionalJobStep3()) //Step3 이동
                    .on("*") //Step3 결과 상관없이
                    .end() //Step3 이동 시 Flow 종료 (FlowBuilder 반환)
                .from(conditionalJobStep1()) //Step1으로부터 (이벤트 리스너)
                    .on("*") //FAILED(ExitStatus) 외 모든 경우
                    .to(conditionalJobStep2()) //Step2로 이동
                    .next(conditionalJobStep3()) //Step2 정상 종료 시 Step3 이동
                    .on("*") //Step3 결과 상관없이
                    .end() //Step3 이동 시 Flow 종료 (FlowBuilder 반환)
                .end() //Job 종료 (FlowBuilder 종료)
                .build();
    }

    @Bean
    public Step conditionalJobStep1() {
        return stepBuilderFactory.get("conditionalJobStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> This is conditionalJob Step1");

                    /**
                     * ExitStatus FAILED 설정 -> 해당 status가 Flow 진행

                     * BatchStatus vs ExitStatus
                      - BatchStatus: Job 또는 Step 실행 결과를 Spring에서 기록할 때 사용하는 Enum
                      - ExitStatus: Step 실행 후 상태를 나타내는 클래스
                     **/
                    contribution.setExitStatus(ExitStatus.FAILED);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get("conditionalJobStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> This is conditionalJob Step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalJobStep3() {
        return stepBuilderFactory.get("conditionalJobStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> This is conditionalJob Step3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
