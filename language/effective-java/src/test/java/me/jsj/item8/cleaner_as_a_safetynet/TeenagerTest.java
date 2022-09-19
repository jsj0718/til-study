package me.jsj.item8.cleaner_as_a_safetynet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeenagerTest {

    @Test
    void testTeenager() {
        new Room(99);
        System.out.println("peace out");

        //GC 호출을 통해 Cleaner 실행
        //GC를 강제로 호출하는 방식에 의존해선 안된다.
//        System.gc();
    }
}