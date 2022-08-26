package me.jsj.thejava.codemanipulation.annotationprocessor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void getterAndSetter() {
        Member member = new Member();

        member.setName("jsj");
        member.setAge(28);

        assertThat(member.getName()).isEqualTo("jsj");
        assertThat(member.getAge()).isEqualTo(28);
    }
}