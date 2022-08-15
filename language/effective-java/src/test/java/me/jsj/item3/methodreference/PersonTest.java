package me.jsj.item3.methodreference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    List<Person> people = new ArrayList<>();

    @BeforeEach
    void setup() {
        people.add(new Person(LocalDate.of(2011, 3, 2)));
        people.add(new Person(LocalDate.of(2013, 1, 28)));
        people.add(new Person(LocalDate.of(1982, 7, 15)));
    }

    private void checkSortResult(List<Person> people) {
        assertEquals(1982, people.get(0).birthday.getYear());
        assertEquals(7, people.get(0).birthday.getMonth().getValue());
        assertEquals(15, people.get(0).birthday.getDayOfMonth());

        assertEquals(2011, people.get(1).birthday.getYear());
        assertEquals(3, people.get(1).birthday.getMonth().getValue());
        assertEquals(2, people.get(1).birthday.getDayOfMonth());

        assertEquals(2013, people.get(2).birthday.getYear());
        assertEquals(1, people.get(2).birthday.getMonth().getValue());
        assertEquals(28, people.get(2).birthday.getDayOfMonth());
    }

    @Test
    void 익명내부클래스() {
        //익명 내부 클래스
        people.sort(new Comparator<Person>() {
            @Override
            public int compare(Person a, Person b) {
                return a.birthday.compareTo(b.birthday);
            }
        });

        checkSortResult(people);
    }

    @Test
    void 람다식() {
        people.sort((p1, p2) -> p1.birthday.compareTo(p2.birthday));

        checkSortResult(people);
    }

    @Test
    void 스태틱메소드참조() {
        people.sort(Person::staticCompareByAge);

        checkSortResult(people);
    }

    @Test
    void 인스턴스메소드참조() {
        Person person = new Person(null);
        people.sort(person::instanceCompareByAge);

        checkSortResult(people);
    }

    @Test
    void 임의객체메소드참조() {
        people.sort(Person::anonymousInstanceCompareByAge);

        checkSortResult(people);
    }

    @Test
    void 생성자메소드참조() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2011, 3, 2));
        dates.add(LocalDate.of(2013, 1, 28));
        dates.add(LocalDate.of(1982, 7, 15));
        List<Person> personList = dates.stream().map(Person::new).collect(Collectors.toList());

        personList.sort(Person::anonymousInstanceCompareByAge);

        checkSortResult(personList);
    }
}