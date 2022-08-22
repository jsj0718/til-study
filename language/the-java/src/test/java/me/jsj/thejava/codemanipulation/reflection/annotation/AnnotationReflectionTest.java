package me.jsj.thejava.codemanipulation.reflection.annotation;

import me.jsj.thejava.codemanipulation.reflection.basic.MyClass;
import me.jsj.thejava.codemanipulation.reflection.basic.MyExtendedClass;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationReflectionTest {

    @Test
    void testAnnotationReflection() {
        //MyClass
        assertThat(MyClass.class.getAnnotations()[0]).isInstanceOf(MyAnnotation.class);

        Arrays.stream(MyClass.class.getDeclaredFields()).forEach(field -> {
            Arrays.stream(field.getAnnotations()).forEach(annotation -> {
                assertThat(annotation).isInstanceOf(MyAnnotation.class);
                if (annotation instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) annotation;
                    assertThat(myAnnotation.value()).isEqualTo("jsj");
                    assertThat(myAnnotation.number()).isEqualTo(10);
                }
            });
        });

        //MyExtendedClass
        assertThat(MyExtendedClass.class.getAnnotations().length).isEqualTo(2);
        assertThat(MyExtendedClass.class.getAnnotations()[0]).isInstanceOf(MyAnnotation.class);
        assertThat(MyExtendedClass.class.getAnnotations()[1]).isInstanceOf(AnotherAnnotation.class);

        assertThat(MyExtendedClass.class.getDeclaredAnnotations().length).isEqualTo(1);
        assertThat(MyExtendedClass.class.getDeclaredAnnotations()[0]).isInstanceOf(AnotherAnnotation.class);
    }
}
