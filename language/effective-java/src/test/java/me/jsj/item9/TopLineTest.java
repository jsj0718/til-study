package me.jsj.item9;

import me.jsj.item9.supress.TopLine;
import org.junit.jupiter.api.Test;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.StreamCorruptedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TopLineTest {

    @Test
    void testTryFinally() throws IOException {
        assertThrows(StreamCorruptedException.class, () -> TopLine.firstLineOfFileUsingTryFinally("build.gradle"));
    }

    @Test
    void testTryWithResources() throws IOException {
        assertThrows(CharConversionException.class, () -> TopLine.firstLineOfFileUsingTryWithResources("build.gradle"));
    }
}