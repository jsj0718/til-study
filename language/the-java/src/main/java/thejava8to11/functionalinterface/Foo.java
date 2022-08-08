package thejava8to11.functionalinterface;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Foo {

/*
    public static void main(String[] args) {
*/
/*
        //익명 내부 클래스
        RunSomething runSomethingV1 = new RunSomething() {
            @Override
            public void doIt() {
                System.out.println("Hello");
            }
        };

        //람다식
        RunSomething runSomethingV2 = () -> {
            System.out.println("Hello");
            System.out.println("World");
        };

        runSomethingV2.doIt();
*/

/*
        int baseNumber = 10;

        RunSomething example = new RunSomething() {
            @Override
            public int sum(int n1, int n2) {
                return n1 + n2 + baseNumber;
            }
        };

//        baseNumber++;
*/

/*
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(10));
*/
/*
        Function<Integer, Integer> plus10 = number -> number + 10;
        System.out.println(plus10.apply(1));

        Function<Integer, Integer> multiply2 = number -> number * 2;
        System.out.println(multiply2.apply(1));

        System.out.println(plus10.compose(multiply2).apply(1));
*/

/*
        Consumer<Integer> printT = number -> System.out.println(number);
        printT.accept(10);
*/

/*
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10.get());
*/

/*
        Predicate<String> startsWithSejin = (s) -> s.startsWith("sejin");
        System.out.println(startsWithSejin.test("j sejin"));

        Predicate<Integer> isEven = (i) -> i % 2 == 0;
        System.out.println(isEven.test(3));
*/

/*
        UnaryOperator<Integer> plus10 = i -> i + 10;
        System.out.println(plus10.apply(10));
*/

/*
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        System.out.println(sum.apply(1, 2));
*/
/*
        Foo foo = new Foo();
        foo.run();
    }
*/
    private void run() {
        int baseNumber = 10;

        //로컬 클래스 (별도의 Scope 생성 -> 쉐도잉으로 인해 같은 이름 변수 선언 시 더 작은 Scope의 변수가 우선권)
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber); //11
            }
        }

        //익명 내부 클래스 (별도의 Scope 생성 -> 쉐도잉으로 인해 같은 이름 변수 선언 시 더 작은 Scope의 변수가 우선권)
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber); //11
            }
        };

        //람다 (람다를 선언한 클래스와 같은 Scope -> 같은 이름의 변수 선언 불가능)
        IntConsumer printInt = (i) -> {
            System.out.println(i + baseNumber);
        };

        printInt.accept(10);
    }
}
