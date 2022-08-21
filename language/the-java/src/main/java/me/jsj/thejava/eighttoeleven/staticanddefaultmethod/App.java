package me.jsj.thejava.eighttoeleven.staticanddefaultmethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

//    public static void main(String[] args) {
/*
        //기본 메소드
        Foo foo = new DefaultFoo("name");
        foo.printName();
        foo.printNameUpperCase();

        //정적 메소드
        Foo.printAnything();
*/


/*
        List<String> names = new ArrayList<>();
        names.add("채치수");
        names.add("강백호");
        names.add("서태웅");
        names.add("송태섭");
        names.add("정대만");
*/
/*
//        names.forEach(System.out::println);

        Spliterator<String> spliterator1 = names.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        while (spliterator1.tryAdvance(System.out::println));
        System.out.println("===========================");
        while (spliterator2.tryAdvance(System.out::println));
*/

/*
        long result = names.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("정"))
                .count();
        System.out.println(result);
*/

/*
        names.removeIf(s -> s.startsWith("채"));
        names.forEach(System.out::println);
*/

/*
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        names.sort(compareToIgnoreCase.reversed());
        names.forEach(System.out::println);
*/

        List<String> names = new ArrayList<>(Arrays.asList("messi", "ronaldo", "pele", "zidane", "ramos"));

/*
        Stream<String> stringStream = names.stream()
                .map(s -> {
                    System.out.println(s);
                    return s.toUpperCase();
                });
*/

/*
        List<String> collect = names.stream()
                .map(s -> {
                    System.out.println(s);
                    return s.toUpperCase();
                }).collect(Collectors.toList());

        System.out.println("===================");

        names.forEach(System.out::println);
*/

//        List<String> collect = names.parallelStream().map(s -> {
//            System.out.println(s + " of thread: " + Thread.currentThread().getName());
//            return s.toUpperCase();
//        }).collect(Collectors.toList());
//
//        collect.forEach(System.out::println);
//    }
}
