package me.jsj.thejava.tests.testtools.junit;

import org.junit.jupiter.api.*;

/**
 * Junit5 테스트 인스턴스
 * - 기본 전략: 메소드마다 클래스 인스턴스를 새로 생성 (테스트 간 의존성을 없애기 위함) => 테스트 간 자원 공유는 없다.
 *
 * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
 * - 정의: 테스트 인스턴스의 라이프 사이클을 클래스로 설정함으로써 성능 향상 가능 (단, 자원 공유가 이뤄진다.)
 * - @BeforeAll, @AfterAll을 static으로 설정할 필요가 없다.
 *
 * @TestMethodOrder
 * - 기본 전략으로는 테스트 간 실행 순서는 알 수 없지만, Order를 통해 순서를 지정할 수 있다.
 * - 시나리오 테스트할 때 유용하다.
 *
 * 이 때, @TestInstance와 @TestMethodOrder는 전혀 상관이 없다. (각각 테스트 자원 공유 여부와 순서 부여 여부의 기능들)
 */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInstanceAndOrderTest {
    
    int value = 1;

    @BeforeAll
    @Disabled
    void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    void afterAll() {
        System.out.println("afterAll");
    }

    @Order(2)
    @Test
    void snake_case에서_under_socre가_없어진다() {
        System.out.println("value++ = " + value++);
    }

    @Order(1)
    @Test
    void test2() {
        System.out.println("value++ = " + value++);
    }

    @Order(4)
    @Test
    void test3() {
        System.out.println("value++ = " + value++);
    }

    @Order(3)
    @Test
    void test4() {
        System.out.println("value++ = " + value++);
    }

    @Order(5)
    @Test
    void test5() {
        System.out.println("value++ = " + value++);
    }
}
