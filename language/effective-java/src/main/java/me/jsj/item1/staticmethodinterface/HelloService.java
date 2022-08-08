package me.jsj.item1.staticmethodinterface;

public interface HelloService {

    String hello();

    //인스턴스에서만 사용 가능
    default String hi() {
        return "hi";
    }

    //인스턴스 생성하지 않고도 사용 가능
    static String bue1() {
        prepareMessage();
        return "bye";
    }

    static String bue2() {
        prepareMessage();
        return "bye";
    }

    static String bue3() {
        prepareMessage();
        return "bye";
    }

    private static void prepareMessage() {
    }
}
