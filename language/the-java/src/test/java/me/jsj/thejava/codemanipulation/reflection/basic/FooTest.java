package me.jsj.thejava.codemanipulation.reflection.basic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class FooTest {
    //System.out.println() 검증을 위한 필드 선언 및 세팅
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testReflection() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        /* Class.forName(String)을 활용하여 Class 타입 객체 생성 */
        Class<?> fooClass = Class.forName("me.jsj.thejava.codemanipulation.reflection.basic.Foo");

        /* Constructor를 활용하여 객체 생성 */
        Constructor<?> constructorNoParameter = fooClass.getConstructor();
        Foo fooV1 = (Foo) constructorNoParameter.newInstance();

        Constructor<?> constructorWithParameter = fooClass.getConstructor(String.class);
        Foo fooV2 = (Foo) constructorWithParameter.newInstance("myFoo");

        /* static 필드 값 가져오기 (인자로 인스턴스를 넘겨야 하지만, static은 인자가 필요없기 때문에 null을 넘기면 된다.) */
        Field staticField = Foo.class.getDeclaredField("publicStaticStringField");
        assertThat(staticField.get(null)).isEqualTo("publicStaticStringField");

        String changeValue = "changeValue";
        staticField.set(null, changeValue);
        assertThat(staticField.get(null)).isEqualTo(changeValue);

        /* 인스턴스 필드 값 가져오기 (private 필드는 접근을 true로 바꿔줘야 한다.) */
        Field field = Foo.class.getDeclaredField("privateStringField");
        field.setAccessible(true);
        assertThat(field.get(fooV1)).isEqualTo("privateStringField");
        assertThat(field.get(fooV2)).isEqualTo("myFoo");

        /* 메소드 가져와서 실행하기 */
        Method method = Foo.class.getDeclaredMethod("privateVoidMethod");
        method.setAccessible(true);
        method.invoke(fooV1);
        assertThat(outContent.toString().trim()).isEqualTo("privateVoidMethod");

        Method sum = Foo.class.getDeclaredMethod("sum", int.class, int.class);
        assertThat((int) sum.invoke(fooV1, 1, 2)).isEqualTo(3);
    }
}