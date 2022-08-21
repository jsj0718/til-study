package me.jsj.thejava.eighttoeleven.functionalinterface;

@FunctionalInterface
public interface RunSomething {

//    void doIt();

    int sum(int n1, int n2);

    static void printName() {
        System.out.println("jsj");
    }

    default void printAge() {
        System.out.println(28 );
    }
}
