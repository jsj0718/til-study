package me.jsj.thejava.tests.testtools.junit;

import org.junit.jupiter.api.*;

/**
 * @DisplayNameGeneration
 * - Method와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정.
 * - 기본 구현체로 ReplaceUnderscores 제공
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BasicAnnotationTest {

    /**
     * @BeforeAll
     * - 모든 테스트 전에 단 한 번만 실행되는 메소드 (static 선언 필수)
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    /**
     * @AfterAll
     * - 모든 테스트 후에 단 한 번만 실행되는 메소드 (static 선언 필수)
     */
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    /**
     * @BeforeEach
     * - 각 테스트 전에 실행되는 메소드
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    /**
     * @AfterEach
     * - 각 테스트 후에 실행되는 메소드
     */
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @Test
    void create1Test() {
        System.out.println("create1");
    }

    @Test
    void create2Test() {
        System.out.println("create2");
    }

    /**
     * @Disabled
     * - 테스트 전체 실행 시 해당 테스트는 실행 예외
     */
    @Test
    @Disabled
    void disabledTest() {
        System.out.println("disabled");
    }

    @Test
    void display_name_generation_test() {
        System.out.println("");
    }

    /**
     * @DisplayName()
     * - 각 메소드의 테스트명 설정
     */
    @Test
    @DisplayName("DisplayName 적용")
    void displayNameTest() {
        System.out.println("displayNameTest");
    }
}
