package me.jsj.item3.staticfactory;

public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    //API 변경 없이 행위를 바꿀 수 있다.
    public static Elvis getInstance() {
        return INSTANCE;
//        return new Elvis();
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    public void sing() {
        System.out.println("I'll have a blue~ Christmas without you");
    }

}
