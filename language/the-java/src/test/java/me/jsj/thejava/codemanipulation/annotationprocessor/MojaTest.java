package me.jsj.thejava.codemanipulation.annotationprocessor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MojaTest {

    @Test
    void testMoja() {
        Moja magicMoja = new MagicMoja();
        assertThat(magicMoja.pollOut()).isEqualTo("Rabbit!");
    }
}