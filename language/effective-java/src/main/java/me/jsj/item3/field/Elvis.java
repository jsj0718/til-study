package me.jsj.item3.field;

import java.io.Serializable;

public class Elvis implements IElvis, Serializable {

    /**
     * 싱글톤 오브젝트
     */
    public static final Elvis INSTANCE = new Elvis();

    private static boolean created;

    private Elvis() {
        if (created) throw new UnsupportedOperationException("can't be created by constructor");
        created = true;
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    public void sing() {
        System.out.println("I'll have a blue~ Christmas without you");
    }

    //역직렬화 시 readResolve()를 사용 -> 오버라이드는 아니다.
    private Object readResolve() {
        return INSTANCE;
    }
}
