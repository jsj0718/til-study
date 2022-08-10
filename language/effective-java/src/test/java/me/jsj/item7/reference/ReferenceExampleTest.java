package me.jsj.item7.reference;

import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceExampleTest {

    @Test
    void softReferenceExample() throws InterruptedException {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(strong);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        //메모리가 충분하기 때문에 삭제되지 않는다.
        assertThat(soft.get()).isNotNull();
    }

    @Test
    void weakReferenceExample() throws InterruptedException {
        Object strong = new Object();
        WeakReference<Object> weak = new WeakReference<>(strong);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        //메모리에 상관없이 strong 결합이 깨지면 GC 대상이 되어 삭제된다.
        assertThat(weak.get()).isNull();
    }

    @Test
    void phantomReferenceExample() throws InterruptedException {
        BigObject strong = new BigObject();
        ReferenceQueue<BigObject> rq = new ReferenceQueue<>();

//        PhantomReference<BigObject> phantom = new PhantomReference<>(strong, rq);
        BigObjectReference<BigObject> phantom = new BigObjectReference<>(strong, rq);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        //대기열에 추가됐는지 여부 판단 -> 큐에 들어 있다면 오브젝트가 사라진 것!
        assertThat(phantom.isEnqueued()).isTrue();

        //팬텀 오브젝트 제거
        Reference<? extends BigObject> reference = rq.poll();
        BigObjectReference bigObjectCleanUp = (BigObjectReference) reference;
        bigObjectCleanUp.cleanUp();
        reference.clear();
    }
}
