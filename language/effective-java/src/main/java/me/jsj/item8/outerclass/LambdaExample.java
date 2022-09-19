package me.jsj.item8.outerclass;

public class LambdaExample {

    private int value = 10;

    protected Runnable instanceLambda = () -> {
        //value를 참조하지 않거나, value가 static 변수라면 바깥 객체를 참조하지 않는다.
        System.out.println("value = " + value);
    };
}
