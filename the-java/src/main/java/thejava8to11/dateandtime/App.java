package thejava8to11.dateandtime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class App {
    public static void main(String[] args) throws InterruptedException {
/*
        Date date = new Date();
        long time = date.getTime();
        System.out.println(date); //Sun Jul 10 23:54:06 KST 2022
        System.out.println(time); //1657464846893

        Thread.sleep(3000);
        Date after3Secs = new Date();
        System.out.println(after3Secs); //Sun Jul 10 23:54:09 KST 2022
        after3Secs.setTime(time);
        System.out.println(after3Secs); //Sun Jul 10 23:54:06 KST 2022

        Calendar calendar = new GregorianCalendar(1995, Calendar.JULY, 18);
        System.out.println(calendar.getTime());
*/

/*
        // 기준 날짜 (UTC)
        Instant instant = Instant.now();
        System.out.println(instant);

        // Asia/Seoul
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);

        // 현재 내 컴퓨터 시간과 동일
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        System.out.println(zonedDateTime);

        //LocalDateTime은 기본적으로 로컬 컴퓨터의 시간 설정값을 따라간다.
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        //of를 통해 특정 날짜로 객체를 생성할 수 있다.
        LocalDateTime birthDay = LocalDateTime.of(1995, Month.JULY, 18, 0, 0, 0);
        System.out.println(birthDay);

        //ZoneDateTime은 now 매개변수로 zoneId를 넘겨서 해당 지역의 날짜와 시간을 갖는 객체를 가져올 수 있다.
        ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println(utcDateTime);
*/

/*
        LocalDate now = LocalDate.now();
        LocalDate birthday = LocalDate.of(2022, Month.JULY, 18);

        //방법1
        Period period = Period.between(now, birthday);
        System.out.println(period.getDays());

        //방법2
        Period until = now.until(birthday);
        System.out.println(until.getDays());
*/

/*
        Instant now = Instant.now();
        Instant plus = now.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(now, plus);
        System.out.println(between.getSeconds());
*/

/*
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        System.out.println(now.format(formatter));

        LocalDate parse = LocalDate.parse("2022.07.18", formatter);
        System.out.println(parse);
*/

        //Date <-> Instant
        Date date = new Date();
        Instant instant = date.toInstant();
        Date newDate = Date.from(instant);

        //GregorianCalendar <-> LocalDate (중간 매개체인 ZoneDateTime 활용)
        Calendar calendar = new GregorianCalendar();
        ZonedDateTime zonedDateTime = calendar.toInstant().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        GregorianCalendar from = GregorianCalendar.from(zonedDateTime);
    }
}
