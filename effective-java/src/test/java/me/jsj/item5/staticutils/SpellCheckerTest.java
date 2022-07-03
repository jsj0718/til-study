package me.jsj.item5.staticutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    @Test
    void isValid() {
        assertFalse(SpellChecker.isValid("test"));
    }
}