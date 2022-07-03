package me.jsj.item4;

public class UtilityClassV2 {
    /**
     *  이 클래스는 인스턴스를 만들 수 없습니다.
     */
    private UtilityClassV2() {
        throw new AssertionError();
    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        String hello = UtilityClassV2.hello();

        UtilityClassV2 utilityClassV2 = new UtilityClassV2();
    }
}
