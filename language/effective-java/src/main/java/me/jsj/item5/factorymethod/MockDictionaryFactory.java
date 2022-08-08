package me.jsj.item5.factorymethod;

import me.jsj.item5.Dictionary;
import me.jsj.item5.MockDictionary;

public class MockDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new MockDictionary();
    }
}
