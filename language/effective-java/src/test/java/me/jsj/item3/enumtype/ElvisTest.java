package me.jsj.item3.enumtype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ElvisTest {

    @Test
    void testEnumElvis() {
        Elvis elvis = Elvis.INSTANCE;
        elvis.leaveTheBuilding();
    }

    @Test
    @DisplayName("Enum 타입은 리플렉션으로 생성자 접근 시 NoSuchMethodException이 발생한다. (실제로 존재하지만 접근을 막아 놓았다.)")
    void testReflectionEnumElvis() {
        assertThrows(NoSuchMethodException.class, () -> {
            Constructor<Elvis> constructor = Elvis.class.getDeclaredConstructor();
        });
    }

    @Test
    void testSerializableEnumElvis() {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("elvis.obj"))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("elvis.obj"))) {
            Elvis elvis = (Elvis) in.readObject();
            assertEquals(Elvis.INSTANCE, elvis);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}