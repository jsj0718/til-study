package me.jsj.practice.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    void invokeMethod() throws Exception {
        String name = "spring";

        //length()
        assertThat(name.length()).isEqualTo(6);

        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer) lengthMethod.invoke(name)).isEqualTo(6);

        //charAt()
        assertThat(name.charAt(0)).isEqualTo('s');

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character) charAtMethod.invoke(name, 0)).isEqualTo('s');
    }
}
