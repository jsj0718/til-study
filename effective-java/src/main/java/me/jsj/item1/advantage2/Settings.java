package me.jsj.item1.advantage2;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private boolean useAutoSteering;

    private boolean useABS;

    private Difficulty difficulty;

/*
    //v1
    //생성자를 사용하면 새로운 인스턴스를 막는 것을 통제할 수 없다. -> 정적 팩토리 메서드로 통제 가능
    public static void main(String[] args) {
        System.out.println(new Settings());
        System.out.println(new Settings());
        System.out.println(new Settings());
    }
*/
    private Settings() {}

    private static final Settings SETTINGS = new Settings();

    public static Settings newInstance() {
        return SETTINGS;
    }

}
