package me.jsj.item3.field;

import me.jsj.item3.staticfactory.MetaElvis;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElvisTest {

    @Test
    void testSingletonUsingPrivateConstructor() {
        Elvis elvis1 = Elvis.INSTANCE;
        Elvis elvis2 = Elvis.INSTANCE;

        assertThat(elvis1).isEqualTo(elvis2);
    }

    @Test
    void testConcert() {
//        Concert concert = new Concert(Elvis.INSTANCE);
        Concert concert = new Concert(new MockElvis()); //Mocking으로 대체
        concert.perform();

        Assertions.assertThat(concert.isLightsOn()).isTrue();
        Assertions.assertThat(concert.isMainStateOpen()).isTrue();
    }

    @Test
    void testSingletonReflect() {
        try {
            Constructor<Elvis> defaultConstructor = Elvis.class.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            
            //created 필드 추가 후 (UnsupportedOperationException에 의해 InvocationTargetException 발생)
            //코드 간결함이 떨어지게 되고, 역직렬화를 통한 인스턴스 생성을 막을 수 없다.
            assertThat(Elvis.INSTANCE).isNotNull();
            assertThrows(InvocationTargetException.class, () -> {
                Elvis elvis1 = defaultConstructor.newInstance();
                Elvis elvis2 = defaultConstructor.newInstance();
            });
/*
            //created 필드 추가 전
            assertThat(elvis1).isNotEqualTo(elvis2);
            assertThat(elvis1).isNotEqualTo(Elvis.INSTANCE);
            assertThat(elvis2).isNotEqualTo(Elvis.INSTANCE);
*/
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSingletonSerializable() {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("elvis.obj"))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("elvis.obj"))) {
            Elvis elvis = (Elvis) in.readObject();
            assertThat(elvis).isEqualTo(Elvis.INSTANCE);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSingletonGeneric() {
        MetaElvis<String> elvis1 = MetaElvis.getInstance();
        MetaElvis<Integer> elvis2 = MetaElvis.getInstance();

        assertThat(elvis1).isEqualTo(elvis2);
        assertThat(elvis1.say("hello")).isEqualTo("hello");
        assertThat(elvis2.say(100)).isEqualTo(100);
    }
}