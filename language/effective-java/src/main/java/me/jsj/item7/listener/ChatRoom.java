package me.jsj.item7.listener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatRoom {

    //잘못된 사용 예시
    private List<WeakReference<User>> users;

    public ChatRoom() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(new WeakReference<>(user));
    }

    public void sendMessage(String message) {
        users.forEach(wr -> Objects.requireNonNull(wr.get()).receive(message));
    }

    public List<WeakReference<User>> getUsers() {
        //Strong하게 참조하는 변수가 없기에 new ChatRoom()은 GC의 대상이 된다.
        ChatRoom localRoom = new ChatRoom();
        localRoom = null;
        return users;
    }
}
