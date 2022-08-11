package me.jsj.item3.staticfactory;

//제네릭 싱글톤 팩토리
public class MetaElvis<T> {

    public static final MetaElvis<Object> INSTANCE = new MetaElvis<>();

    private MetaElvis() {
    }

    public static <E> MetaElvis<E> getInstance() {
        return (MetaElvis<E>) INSTANCE;
    }

    public T say(T t) {
        return t;
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    public void sing() {
        System.out.println("I'll have a blue~ Christmas without you");
    }

}
