package me.jsj.practice.factorybean;

import lombok.Getter;

@Getter
public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    //Message 객체를 만들기 위해서는 해당 메소드를 사용해야 한다.
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
