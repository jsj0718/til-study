package me.jsj.item3.staticfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcertTest {

    @Test
    void testConcert() {
        Concert concert = new Concert();
        concert.start(Elvis::getInstance);
    }
}