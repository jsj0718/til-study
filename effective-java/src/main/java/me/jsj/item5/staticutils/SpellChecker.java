package me.jsj.item5.staticutils;

import me.jsj.item5.DefaultDictionary;
import me.jsj.item5.Dictionary;

import java.util.List;

public class SpellChecker {
    private static final Dictionary DICTIONARY = new DefaultDictionary();

    private SpellChecker() {}

    public static boolean isValid(String word) {
        return DICTIONARY.contains(word);
    }

    public static List<String> suggestions(String typo) {
        return DICTIONARY.closeWordsTo(typo);
    }

}
