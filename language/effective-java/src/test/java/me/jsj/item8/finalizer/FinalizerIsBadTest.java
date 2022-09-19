package me.jsj.item8.finalizer;

import org.junit.jupiter.api.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class FinalizerIsBadTest {

    @Test
    void testFinalize() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        int i = 0;
        int count = 0;
        while(true) {
            i++;
            new FinalizerIsBad();
            if ((i % 1000000) == 0) {
                if (count++ > 10) break;
                Class<?> finalizerClass = Class.forName("java.lang.ref.Finalizer");
                Field queueStaticField = finalizerClass.getDeclaredField("queue");
                queueStaticField.setAccessible(true);
                ReferenceQueue referenceQueue = (ReferenceQueue) queueStaticField.get(null);

                Field queueLengthField = ReferenceQueue.class.getDeclaredField("queueLength");
                queueLengthField.setAccessible(true);
                long queueLength = (long) queueLengthField.get(referenceQueue);
                System.out.format("There are %d references in the queue\n", queueLength);
            }
        }
    }
}