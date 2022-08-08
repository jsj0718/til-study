package me.jsj.item5.springioc;

import me.jsj.item5.Dictionary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringDictionary implements Dictionary {
    @Override
    public boolean contains(String word) {
        System.out.println("contains " + word);
        return false;
    }

    @Override
    public List<String> closeWordsTo(String typo) {
        return null;
    }
}
