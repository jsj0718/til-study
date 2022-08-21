package me.jsj.thejava.codemanipulation.jvmstructure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void testHasParentClass() {
        assertThat(App.class.getSuperclass()).isEqualTo(Object.class);
    }

    @Test
    void testClassLoader() {
        ClassLoader classLoader = App.class.getClassLoader();
        assertThat(classLoader).isEqualTo(ClassLoader.getSystemClassLoader()); //AppClassLoader
        assertThat(classLoader.getParent()).isEqualTo(ClassLoader.getPlatformClassLoader()); //PlatformClassLoader
        assertThat(classLoader.getParent().getParent()).isNull(); //BootLoader가 존재하지만 native 코드로 구현되어 조회 불가능
    }
}