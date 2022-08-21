package me.jsj.thejava.codemanipulation.jvmstructure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsjTest {

    @Test
    void testStaticFieldInit() {
        Jsj jsj = new Jsj();
        assertThat(jsj.work()).isEqualTo("jsj");
    }
}