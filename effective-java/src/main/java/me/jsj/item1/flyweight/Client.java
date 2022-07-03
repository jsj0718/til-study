package me.jsj.item1.flyweight;

public class Client {
    public static void main(String[] args) {
        FontFactory fontFactory = new FontFactory();
        CharacterV2 c1 = new CharacterV2('h', "black", fontFactory.getFont("nanum:12"));
        CharacterV2 c2 = new CharacterV2('e', "black", fontFactory.getFont("nanum:12"));
        CharacterV2 c3 = new CharacterV2('l', "black", fontFactory.getFont("nanum:12"));
        CharacterV2 c4 = new CharacterV2('l', "black", fontFactory.getFont("nanum:12"));
        CharacterV2 c5 = new CharacterV2('o', "black", fontFactory.getFont("nanum:12"));
    }
}
