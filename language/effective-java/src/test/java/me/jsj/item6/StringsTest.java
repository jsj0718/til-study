package me.jsj.item6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringsTest {

    @Test
    void equalsTest() {
        assertThat(Strings.isEquals("hello", "hello")).isTrue();
        assertThat(Strings.isEquals("hello", new String("hello"))).isFalse();
        assertThat(Strings.isEquals(new String("hello"), new String("hello"))).isFalse();
    }
}