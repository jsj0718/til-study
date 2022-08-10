package me.jsj.item6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegularExpressionTest {

    @Test
    void diffSplitTime() {
        assertThat(RegularExpression.getSplitTimeUsingPattern("정대만,강백호,서태웅"))
                .isGreaterThan(RegularExpression.getSplitTime("정대만,강백호,서태웅", ","));
    }
}