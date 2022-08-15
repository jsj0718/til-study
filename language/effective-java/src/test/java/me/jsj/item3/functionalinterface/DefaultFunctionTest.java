package me.jsj.item3.functionalinterface;

import me.jsj.item3.methodreference.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultFunctionTest {

    @Test
    void testFunction() {
        Function<LocalDate, Person> personFunction = Person::new;
        Person person = personFunction.apply(LocalDate.of(1995, 7, 18));

        assertThat(person.getBirthday()).isEqualTo(LocalDate.of(1995, 7, 18));
    }

    @Test
    void testSupplier() {
        Supplier<Person> personSupplier = Person::new;
        Person person = personSupplier.get();

        assertThat(person.getBirthday()).isNull();
    }

    @Test
    void testConsumer() {
        Consumer<Person> personConsumer = System.out::println;
        personConsumer.accept(new Person());
        personConsumer.accept(new Person(LocalDate.of(1995, 7, 18)));
    }

    @Test
    void testPredicate() {
        Predicate<LocalDate> datePredicate = date -> date.isBefore(LocalDate.of(2000, 1, 1));

        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2011, 3, 2));
        dates.add(LocalDate.of(2013, 1, 28));
        dates.add(LocalDate.of(1982, 7, 15));

        List<Integer> before2000 = dates.stream()
                .filter(datePredicate)
                .map(LocalDate::getYear)
                .collect(Collectors.toList());

        assertThat(before2000.size()).isEqualTo(1);
        assertThat(before2000.get(0)).isEqualTo(1982);
    }
}
