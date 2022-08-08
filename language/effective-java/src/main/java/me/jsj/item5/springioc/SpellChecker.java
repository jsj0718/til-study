package me.jsj.item5.springioc;

import me.jsj.item5.Dictionary;
import org.springframework.stereotype.Component;

import java.util.List;

// 스프링은 POJO 상태를 유지하길 원한다.
// 프레임워크가 코드에 노출되는 것을 침투형 프레임워크라고 한다. (e.x. EJB)

@Component
public class SpellChecker {

    private Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        return dictionary.closeWordsTo(typo);
    }

}
