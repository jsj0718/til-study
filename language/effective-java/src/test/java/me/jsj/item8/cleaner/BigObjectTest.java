package me.jsj.item8.cleaner;

import org.junit.jupiter.api.Test;

import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BigObjectTest {

    @Test
    void testCleaner() throws InterruptedException {
        Cleaner cleaner = Cleaner.create();

        List<Object> resourceToCleanUp = new ArrayList<>();
        BigObject bigObject = new BigObject(resourceToCleanUp);

        cleaner.register(bigObject, new BigObject.ResourceCleaner(resourceToCleanUp));

        bigObject = null;
        System.gc();
        Thread.sleep(3000L);
    }
}