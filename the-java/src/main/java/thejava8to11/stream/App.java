package thejava8to11.stream;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> whiteshipEvent = new ArrayList<>();

        whiteshipEvent.add(springClasses);
        whiteshipEvent.add(javaClasses);

        System.out.println(">>>>>> spring 으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(oc -> System.out.println(oc.getTitle()));


        System.out.println(">>>>>> close 되지 않은 수업");
        springClasses.stream()
//                .filter(oc -> !oc.isClosed())
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(oc -> System.out.println(oc.getTitle()));

        System.out.println(">>>>>> 수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(oc -> oc.getTitle())
                .forEach(System.out::println);

        //flatten 작업을 통해 서로 다른 두 리스트의 데이터들을 꺼내올 수 있다.
        System.out.println(">>>>>> 두 수업 목록에 들어 있는 모든 수업 아이디 출력");
        whiteshipEvent.stream()
                .flatMap(Collection::stream)
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println(">>>>>> 10부터 1 증가하는 무제한 스트림 중 앞에 10개 빼고 최대 10개까지만");
        Stream.iterate(10, i -> i + 1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);


        System.out.println(">>>>>> 자바 수업 중 Test가 들어 있는 수업이 있는지 확인");
        javaClasses.stream()
                .filter(oc -> oc.getTitle().contains("Test"))
                .forEach(oc -> System.out.println(oc.getTitle()));

        boolean test = javaClasses.stream()
                .anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println(">>>>>> 스프링 수업 중 제목에 spring이 들어간 것만 모아서 List 만들기");
        List<String> lectureContainsSpring = springClasses.stream()
                .filter(oc -> oc.getTitle().contains("spring"))
                .map(oc -> oc.getTitle())
                .collect(Collectors.toList());

        lectureContainsSpring.forEach(System.out::println);
    }
}