package me.jsj.item3.methodreference;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class Person {

    LocalDate birthday;

    public Person() {
    }

    public Person(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }

    public static int staticCompareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    public int instanceCompareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    //임의객체 메소드 참조는 첫 번째 인자가 자기 자신이다.
    public int anonymousInstanceCompareByAge(Person p) {
        return this.birthday.compareTo(p.birthday);
    }
}
