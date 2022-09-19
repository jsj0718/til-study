package me.jsj.item8.cleaner_as_a_safetynet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdultTest {

    //cleaner 안전망을 갖춘 자원을 제대로 활용하는 클라이언트 테스트
    @Test
    void testAdult() {
        try (Room myRoom = new Room(7)) {
            System.out.println("안녕~");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}