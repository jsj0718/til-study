package me.jsj.item5.dependencyinjection;

import me.jsj.item5.DefaultDictionary;
import me.jsj.item5.DictionaryFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    @Test
    void isValid() {
//        SpellChecker spellChecker = new SpellChecker(() -> new DefaultDictionary());
//        SpellChecker spellChecker = new SpellChecker(DefaultDictionary::new);

//        SpellChecker spellChecker = new SpellChecker(() -> DictionaryFactory.get());
        SpellChecker spellChecker = new SpellChecker(DictionaryFactory::get);

        assertFalse(spellChecker.isValid("word"));
        assertNull(spellChecker.suggestions("typo"));
    }
}