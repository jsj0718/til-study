package me.jsj.thejava.tests.testtools.junit;

import me.jsj.thejava.tests.testtools.junit.common.FindSlowTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * 확장 방법 1. @ExtendWith
 * - 커스터마이징을 할 수 없다. (Default 생성자로 인스턴스 생성 후 활용하기 떄문)
 *
 * 확장 방법 2. @RegisterExtension
 * - 커스터마이징 가능 (매개변수를 줄 수 있기 때문)
 * 
 * 확장 방법 3. ServiceLoader 설정 (자동으로 확장팩 인식 -> properties에서 설정 가능)
 * - 그러나 명시적으로 선언하는 것이 더 좋다. (최대한 사용 X)
 */
//@ExtendWith(FindSlowTestExtension.class)
public class ExtensionTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);


    @Test //메세지 출력 O
//    @SlowTest // 메세지 출력 X
    void findSlowTagExtensionTest() throws InterruptedException {
        Thread.sleep(1005L);
    }
}
