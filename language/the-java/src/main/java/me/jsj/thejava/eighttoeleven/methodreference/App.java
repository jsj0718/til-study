package me.jsj.thejava.eighttoeleven.methodreference;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class App {
/*
    public static void main(String[] args) {
        //정적 메소드 레퍼런스 참조
        UnaryOperator<String> hi = Greeting::hi;
        System.out.println(hi.apply("world")); //hi world

        //인스턴스 메소드 레퍼런스 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;
        System.out.println(hello.apply("world")); //hello world

        //생성자 참조 (매개변수 없는 경우)
        Supplier<Greeting> greetingSupplier = Greeting::new; //객체를 생성한 것이 아니다! (참조 했을 뿐 get()을 호출해야 생성된다.)
        Greeting greetingBySupplier = greetingSupplier.get();
        System.out.println(greetingBySupplier.getName()); //null

        //생성자 참조 (매개변수가 있는 경우)
        Function<String, Greeting> greetingFunction = Greeting::new;
        Greeting greetingByFunction = greetingFunction.apply("world");
        System.out.println(greetingByFunction.getName()); //world


        //임의 객체의 인스턴스 메소드 참조
        String[] names = {"b", "a", "d", "C"};
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));
    }
*/
}
