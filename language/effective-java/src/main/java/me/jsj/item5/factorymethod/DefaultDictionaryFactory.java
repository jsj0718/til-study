package me.jsj.item5.factorymethod;

import me.jsj.item5.DefaultDictionary;
import me.jsj.item5.Dictionary;

public class DefaultDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new DefaultDictionary();
    }
}
