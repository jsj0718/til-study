package me.jsj.thejava.codemanipulation.bytecode.masul;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MasulsaTest {

/*
    //V1의 경우 redefine에서 Moja.class를 이미 읽기 때문에 기존 클래스의 바이트 코드가 사용된다. -> 사전에 한 번 실행해야 하는 이유
    @BeforeEach
    void changeByteCodeV1() throws IOException {
        new ByteBuddy().redefine(Moja.class)
                .method(named("pollOut")).intercept(FixedValue.value("Rabbit!"))
                .make().saveIn(new File("/Users/sejinjeong/devtool/project/til-study/language/the-java/out/production/classes/")); //IntelliJ
//                .make().saveIn(new File("/Users/sejinjeong/devtool/project/til-study/language/the-java/build/classes/java/main/")); //Gradle
    }
*/

/*
    //V2에서는 이를 클래스 로더에서 String을 활용하여 해결할 수 있다.
    @BeforeEach
    void changeByteCodeV2() throws IOException {
        ClassLoader classLoader = Masulsa.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);

        new ByteBuddy().redefine(typePool.describe("me.jsj.thejava.codemanipulation.bytecode.masul.Moja").resolve(), ClassFileLocator.ForClassLoader.of(classLoader))
                .method(named("pollOut")).intercept(FixedValue.value("Rabbit!"))
                .make().saveIn(new File("/Users/sejinjeong/devtool/project/til-study/language/the-java/out/production/classes/")); //IntelliJ
    }
*/

    @Test
    void testMasul() {
        Masulsa masulsa = new Masulsa();
        System.out.println(">>>>>> Moja 내용: " + masulsa.openMoja());
        if (masulsa.openMoja().isEmpty()) {
            //[V1] out 폴더가 없는 경우 기존 클래스의 바이트 코드가 적용되기 때문에 빈 값이 나온다.
            assertThat(masulsa.openMoja()).isEqualTo("");
        } else {
            //[V1] out 폴더가 존재하는 경우 기존 클래스의 바이트 코드가 교체되기 때문에 Rabbit!이 나온다.
            //[V2] Moja 클래스 로딩 전에 바이트 코드를 교체하기 때문에 (클래스 로더와 String 활용) out 폴더 상관없이 교체된다.
            //[V2] 그러나 바이트 코드 교체 전에 다른 곳에서 클래스를 로드했다면 이 또한 기존 클래스의 바이트 코드를 사용하게 된다. (클래스 로딩 시점에 의존적)
            //[V3] VM Option으로 javaagent 설정하면 기존 바이트 코드 수정 없이 비침투적으로 변경된 내용을 사용할 수 있다.
            assertThat(masulsa.openMoja()).isEqualTo("Rabbit!");
        }
    }
}