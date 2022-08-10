package me.jsj.item7.listener;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChatRoomTest {

    @Test
    void chatRoom() throws InterruptedException {
        ChatRoom chatRoom = new ChatRoom();
        User user1 = new User();
        User user2 = new User();

        chatRoom.addUser(user1);
        chatRoom.addUser(user2);

        chatRoom.sendMessage("hello");

        //user1이 떠난 경우 자원 해제를 해준다면 리스너 명단에서 제거가 된다.
        user1 = null;

        System.gc();

        Thread.sleep(5000L);

        List<WeakReference<User>> users = chatRoom.getUsers();
        assertThat(users.get(0).get()).isNull();
        //List에서 null인 WeakReference를 제거하기 위해서는 별도로 로직 구현이 필요하다.
        assertThat(users.size()).isEqualTo(2);
    }
}