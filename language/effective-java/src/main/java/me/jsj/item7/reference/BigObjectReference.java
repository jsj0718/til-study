package me.jsj.item7.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class BigObjectReference<BigObject> extends PhantomReference<BigObject> {
    public BigObjectReference(BigObject referent, ReferenceQueue<? super BigObject> q) {
        super(referent, q);
    }

    //자원 정리 메소드
    public void cleanUp() {
        System.out.println("clean up ");
    }
}