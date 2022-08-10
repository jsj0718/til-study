package thejava8to11.methodreference;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    @Test
    void methodReferenceTest() {
        //정적 메소드 레퍼런스 참조
        UnaryOperator<String> hi = Greeting::hi;
        assertThat(hi.apply("world")).isEqualTo("hi world");

        //인스턴스 메소드 레퍼런스 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;
        assertThat(hello.apply("world")).isEqualTo("hello world");

        //생성자 참조 (매개변수 없는 경우)
        Supplier<Greeting> greetingSupplier = Greeting::new; //객체를 생성한 것이 아니다! (참조 했을 뿐 get()을 호출해야 생성된다.)
        Greeting greetingBySupplier = greetingSupplier.get();
        assertThat(greetingBySupplier.getName()).isNull();

        //생성자 참조 (매개변수가 있는 경우)
        Function<String, Greeting> greetingFunction = Greeting::new;
        Greeting greetingByFunction = greetingFunction.apply("world");
        assertThat(greetingByFunction.getName()).isEqualTo("world");

        //임의 객체의 인스턴스 메소드 참조
        String[] names = {"b", "a", "d", "C"};
        Arrays.sort(names, String::compareToIgnoreCase);
        assertThat(names[0]).isEqualTo("a");
        assertThat(names[1]).isEqualTo("b");
        assertThat(names[2]).isEqualTo("C");
        assertThat(names[3]).isEqualTo("d");
    }
}