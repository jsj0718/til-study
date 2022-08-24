package me.jsj.thejava.codemanipulation.reflection.basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {
    MyClass myClass;

    Class<MyClass> classV1;

    Class<? extends MyClass> classV2;

    Class<?> classV3;

    @BeforeEach
    void setup() throws ClassNotFoundException {
        //클래스 객체 가져오는 방법1. 클래스명.class를 통해 가져온다. (클래스 로딩 시 힙 영역에 추가해줌)
        classV1 = MyClass.class;

        //클래스 객체 가져오는 방법2. 인스턴스의 getClass() 메소드로 가져온다.
        myClass = new MyClass();
        classV2 = myClass.getClass();

        //클래스 객체 가져오는 방법3. Class.forName()과 FQCN 활용
        classV3 = Class.forName("me.jsj.thejava.codemanipulation.reflection.basic.MyClass");
    }

    @Test
    void testFieldReflection() throws ClassNotFoundException, NoSuchFieldException {
        //필드 배열 선언
        Field[] fields = classV1.getFields();
        Field[] declaredFields = classV1.getDeclaredFields();

        //public field들만 가져온다.
        Arrays.stream(fields).forEach(field ->
                assertThat(field.getName()).isEqualTo("publicField"));

        //선언된 모든 field를 가져오고 값을 확인한다.
        assertThat(declaredFields.length).isEqualTo(5);
        assertThat(declaredFields[0].getName()).isEqualTo("privateStaticField");
        assertThat(declaredFields[1].getName()).isEqualTo("privateStaticFinalField");
        assertThat(declaredFields[2].getName()).isEqualTo("privateField");
        assertThat(declaredFields[3].getName()).isEqualTo("publicField");
        assertThat(declaredFields[4].getName()).isEqualTo("protectedField");

        try {
            declaredFields[0].setAccessible(true);
            assertThat(declaredFields[0].get(myClass)).isEqualTo("privateStaticField");
            declaredFields[1].setAccessible(true);
            assertThat(declaredFields[1].get(myClass)).isEqualTo("privateStaticFinalField");
            declaredFields[2].setAccessible(true);
            assertThat(declaredFields[2].get(myClass)).isEqualTo("privateField");
            declaredFields[3].setAccessible(true);
            assertThat(declaredFields[3].get(myClass)).isEqualTo("publicField");
            declaredFields[4].setAccessible(true);
            assertThat(declaredFields[4].get(myClass)).isEqualTo("protectedField");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //지점한 이름의 필드만 가져온다.
        assertThat(classV1.getDeclaredField("privateStaticField").getName()).isEqualTo("privateStaticField");
        assertThat(classV1.getDeclaredField("privateStaticFinalField").getName()).isEqualTo("privateStaticFinalField");
        assertThat(classV1.getDeclaredField("privateField").getName()).isEqualTo("privateField");
        assertThat(classV1.getDeclaredField("publicField").getName()).isEqualTo("publicField");
        assertThat(classV1.getDeclaredField("protectedField").getName()).isEqualTo("protectedField");

        //private과 static 여부를 판단한다.
        Arrays.stream(MyClass.class.getDeclaredFields()).forEach(f -> {
            int modifiers = f.getModifiers();

            if (f.getName().toLowerCase().contains("private")) {
                assertThat(Modifier.isPrivate(modifiers)).isTrue();
            } else {
                assertThat(Modifier.isPrivate(modifiers)).isFalse();
            }
            if (f.getName().toLowerCase().contains("static")) {
                assertThat(Modifier.isStatic(modifiers)).isTrue();
            } else {
                assertThat(Modifier.isStatic(modifiers)).isFalse();
            }
        });
    }

    @Test
    void testMethodReflection() {
        //메소드 이름을 가져온다.
        Arrays.stream(classV1.getMethods()).forEach(System.out::println);
        assertThat(Arrays.stream(classV1.getDeclaredMethods()).map(method -> method.getName())).contains("privateMethod");
        assertThat(Arrays.stream(classV1.getDeclaredMethods()).map(method -> method.getName())).contains("publicMethod");
        assertThat(Arrays.stream(classV1.getDeclaredMethods()).map(method -> method.getName())).contains("publicIntegerMethod");
    }

    @Test
    void testConstructorReflection() {
        //생성자를 가져온다.
        Arrays.stream(classV1.getConstructors()).forEach(System.out::println);
        assertThat(Arrays.stream(classV1.getConstructors()).map(constructor -> constructor.getName())).contains("me.jsj.thejava.codemanipulation.reflection.basic.MyClass");
    }

    @Test
    void testSuperClassReflection() {
        assertThat(MyClass.class.getSuperclass()).isEqualTo(Object.class);
        assertThat(MyExtendedClass.class.getSuperclass()).isEqualTo(MyClass.class);
        assertThat(MyExtendedClass.class.getInterfaces()).contains(MyInterface.class);
    }

    @Test
    void testLocalDateTime() {
        //LocalDate
        LocalDate before61DaysFromNow = LocalDate.now().minusDays(61);
        LocalDate before60DaysFromNow = LocalDate.now().minusDays(60);
        LocalDate before59DaysFromNow = LocalDate.now().minusDays(59);

        assertThat(isAfter60Days(before61DaysFromNow)).isTrue();
        assertThat(isAfter60Days(before60DaysFromNow)).isFalse();
        assertThat(isAfter60Days(before59DaysFromNow)).isFalse();

        //String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String before61DaysFromNowString = before61DaysFromNow.format(formatter);
        String before60DaysFromNowString = before60DaysFromNow.format(formatter);
        String before59DaysFromNowString = before59DaysFromNow.format(formatter);
        assertThat(isAfter60Days(before61DaysFromNowString, formatter)).isTrue();
        assertThat(isAfter60Days(before60DaysFromNowString, formatter)).isFalse();
        assertThat(isAfter60Days(before59DaysFromNowString, formatter)).isFalse();
    }

    private boolean isAfter60Days(String date, DateTimeFormatter formatter) {
        LocalDate regDate = LocalDate.parse(date, formatter);
        return ChronoUnit.DAYS.between(regDate, LocalDate.now()) > 60;
    }

    private boolean isAfter60Days(LocalDate date) {
        return ChronoUnit.DAYS.between(date, LocalDate.now()) > 60;
    }
}