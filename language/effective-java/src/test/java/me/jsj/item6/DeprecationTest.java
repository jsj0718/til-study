package me.jsj.item6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeprecationTest {

    @Test
    void deprecationTest() {
        Deprecation deprecation = new Deprecation();
        Deprecation notDeprecation = new Deprecation("name");
    }
}