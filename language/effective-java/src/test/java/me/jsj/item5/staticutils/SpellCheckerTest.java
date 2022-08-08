package me.jsj.item5.staticutils;

import me.jsj.item5.staticutils.SpellChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SpellCheckerTest {

    @Test
    void isValid() {
        assertFalse(SpellChecker.isValid("test"));
    }
}