package thejava8to11.staticanddefaultmethod;

public interface Bar {
    default void printNameUpperCase() {
        System.out.println("BAR");
    }
}
