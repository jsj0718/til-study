package me.jsj.item1.flyweight;

public class Character {
    //주로 변경
    private char value;

    private String color;

    //거의 변경 X
    private String fontFamily;

    private int fontSize;

    public Character(char value, String color, String fontFamily, int fontSize) {
        this.value = value;
        this.color = color;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
