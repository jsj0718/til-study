package me.jsj.item1.advantage3;

public class HelloServiceFactory {

    public static HelloService of(String lang) {
        if (lang.equals("ko")) {
            return new KoreanHelloService();
        } else {
            return new EnglishHelloService();
        }
    }

/*
    public static void main(String[] args) {
        HelloService ko = HelloService.of("ko");
        HelloService eng = HelloService.of("eng");

        System.out.println(ko.hello());
        System.out.println(eng.hello());
    }
*/
}
