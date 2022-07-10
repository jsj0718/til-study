package thejava8to11.staticanddefaultmethod;

public interface Foo {

    void printName();

    /**
     * @ImplSpec
     * 이 구현체는 getName()으로 가져온 문자를 대문자로 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    String getName();

    static void printAnything() {
        System.out.println("Foo");
    }

}
