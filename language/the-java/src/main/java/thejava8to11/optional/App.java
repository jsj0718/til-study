package thejava8to11.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

/*
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> onlineClassOptional = springClasses.stream()
                .filter(oc -> oc.getTitle().contains("spring"))
                .findFirst();
*/

//        OnlineClass onlineClass = onlineClassOptional.get();
//        System.out.println(onlineClassOptional.isPresent());
//        System.out.println(onlineClass.getTitle());

//        onlineClassOptional.ifPresent(oc -> System.out.println(oc.getTitle()));

//        OnlineClass onlineClass = onlineClassOptional.orElseGet(App::createNewOnlineClass);
//        System.out.println(onlineClass.getTitle());

//        OnlineClass onlineClass = onlineClassOptional.orElseThrow(IllegalStateException::new);

//        Optional<OnlineClass> onlineClass = onlineClassOptional.filter(oc -> oc.getId() > 10);
//        System.out.println(onlineClass.isEmpty());

/*
        Optional<Optional<Progress>> progress = onlineClassOptional.map(OnlineClass::getProgress);
        Optional<Progress> progress1 = onlineClassOptional.flatMap(OnlineClass::getProgress);
    }
*/

    private static OnlineClass createNewOnlineClass() {
        System.out.println("createNewOnlineClass 동작");
        return new OnlineClass(10, "The Java, Java 8", false);
    }
}
