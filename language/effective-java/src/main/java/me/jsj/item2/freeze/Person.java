package me.jsj.item2.freeze;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
public class Person {

    private final String name;
    private final int birthYear;
    private final List<String> grade;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.grade = new ArrayList<>();
    }

/*
    public static void main(String[] args) {
        final Person person = new Person("jsj", 1995);
//        person.name = "정세진";
        log.info(person.toString());

//        person = new Person("세진", 1995);
    }
*/
}
