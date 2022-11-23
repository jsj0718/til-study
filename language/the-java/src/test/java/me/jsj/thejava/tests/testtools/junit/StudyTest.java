package me.jsj.thejava.tests.testtools.junit;

import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyStatus;
import me.jsj.thejava.tests.testtools.junit.common.FastTest;
import me.jsj.thejava.tests.testtools.junit.common.SlowTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class StudyTest {

    /**
     * assertNotNull
     * - Null이 아닌지 여부를 판단
     * 
     * assertEquals
     * - Expected 값과 Actual 값 비교 후 동일 여부 판단
     * - 메세지를 추가적으로 넣을 수 있다. (통과 조건) => 메세지에 String 연산 작업이 존재하다면 Supplier를 쓰는 것이 좋다. (필요한 순간에만 연산 진행)
     *
     * assertTrue
     * - 해당 조건이 참인지 여부 판단
     * - 메세지를 추가적으로 넣을 수 있다. (통과 조건) => 메세지에 String 연산 작업이 존재하다면 Supplier를 쓰는 것이 좋다. (필요한 순간에만 연산 진행)
     *
     * 단, 중간에 테스트가 실패하면 그 이후 테스트는 실행되지 않는다.
     */
    @Test
    @DisplayName("스터디 만들기 - Basic")
    void createNewStudyV1() {
        Study study = new Study(5);
        assertNotNull(study);
        Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태는 DRAFT여야 한다.");
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.");
    }

    /**
     * assertAll
     * - V1의 문제점을 해결할 수 있는 방안
     * - assertAll 내부에 있는 테스트들은 중간에 테스트가 실패하더라도 진행된다.
     */
    @Test
    @DisplayName("스터디 만들기 - assertAll")
    void createNewStudyV2() {
        Study study = new Study(5);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태는 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.")
        );
    }

    /**
     * assertThrows
     * - 해당 Excutable이 실패했을 때 반환되는 타입으로 검증 가능
     * - 또한 반환된 예외 객체에서 메세지를 꺼내 기대값과 비교 가능
     */
    @Test
    @DisplayName("스터디 만들기 - Exception")
    void createNewStudyException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(0));
        assertEquals("스터디의 최대 인원은 0명 이상이여야 합니다.", exception.getMessage());
    }

    /**
     * assertTimeout
     * - Timeout 테스트
     * - 단, 테스트는 Timeout으로 설정된 시간이 아닌 모든 작업이 종료하기까지의 시간이 소요된다.
     */
    @Test
    @DisplayName("스터디 만들기 - Timeout")
    void createNewStudyTimeout() {
        //Timeout으로 설정된 시간이 아닌 작업하는데 걸린 시간만큼 소요된다.
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
//            Thread.sleep(300);
        });
    }

    /**
     * assertTimeoutPreemptively
     * - Timeout 테스트
     * - 테스트는 Timeout으로 설정된 시간이 소요된다.
     * - 단, ThreadLocal 방식이므로 별개의 쓰레드로 구동되기 때문에 설정된 값과 다르게 동작할 수 있다. (주의!)
     */
    @Test
    @DisplayName("스터디 만들기 - TimeoutPreemptively")
    void createNewStudyTimeoutPreemptively() {
        //Timeout으로 설정된 시간이 아닌 작업하는데 걸린 시간만큼 소요된다.
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
//            Thread.sleep(300);
        });
    }

    /**
     * assumeTrue
     * - 해당 조건에 부합하는 경우에만 테스트 진행
     * 
     * Assuming
     * - 해당 조건에 맞는 경우 Executable 실행
     */
    @Test
    @DisplayName("스터디 만들기 - Assume, Assuming")
    void createNewStudyAssume() {
//        assumeTrue("LOCAL".equalsIgnoreCase(System.getenv("TEST_ENV")));

        //given
        String testEnv = "LOCAL";

        //assumeTrue
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));
        Study study = new Study(10);
        assertAll(
                () -> assertThat(study).isNotNull(),
                () -> assertThat(study.getStatus()).isEqualTo(StudyStatus.DRAFT),
                () -> assertThat(study.getLimit()).isEqualTo(10)
        );

        //assumingThat
        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> System.out.println("LOCAL 실행"));
        assumingThat("QA".equalsIgnoreCase(testEnv), () -> System.out.println("QA 실행"));
        assumingThat("PROD".equalsIgnoreCase(testEnv), () -> System.out.println("PROD 실행"));
    }

    /**
     * @EnabledOnOS
     * - 해당 OS에만 테스트 실행
     * 
     * @EnabledOnJre
     * - 해당 JRE 환경에서만 테스트 실행
     *
     * @EnabledIfEnvironmentVariable
     * - 환경변수가 매칭되는 경우에만 테스트
     */
    @Test
    @DisplayName("스터디 만들기 - EnabledOnOS, EnabledOnJre, EnabledIfEnvironmentVariable")
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void createNewStudyEnabledOnOS() {
        System.out.println("OS, JRE, Environment Test");
    }

    /**
     * @DisabledOnOS
     * - 해당 OS는 테스트 예외
     * 
     * @DisabledOnJre
     * - 해당 JRE 환경은 테스트 예외
     */
    @Test
    @DisplayName("스터디 만들기 - DisabledOnOS, DisabledOnJre")
    @DisabledOnOs({OS.MAC})
    @DisabledOnJre({JRE.OTHER})
    void createNewStudyDisabledOnOS() {
        System.out.println("OS, JRE, Environment Test");
    }

    /**
     * @Tag()
     * - 해당 태그가 붙은 테스트만 실행할 수 있게 해주는 애노테이션
     * - 방법: Edit Configurations -> Build And Run에서 Class 대신 Tag 선택 -> 설정한 태그값 지정
     * - Profile에 따라 태깅을 통해 테스트 여부를 설정할 수 있다.
     * - 애노테이션을 생성 후 커스터마이징도 가능하다.
     */
    @Test
    @DisplayName("스터디 만들기 - Fast")
    @Tag("fast")
    void createNewStudyFast() {
        System.out.println("Fast");
    }

    @Test
    @DisplayName("스터디 만들기 - Slow")
    @Tag("slow")
    void createNewStudySlow() {
        System.out.println("Slow");
    }

    @Test
    @DisplayName("스터디 만들기 - Custom Fast")
    @FastTest
    void createNewStudyCustomFast() {
        System.out.println("Fast");
    }

    @Test
    @DisplayName("스터디 만들기 - Custom Slow")
    @SlowTest
    void createNewStudyCustomSlow() {
        System.out.println("Slow");
    }
}
