package me.jsj.thejava.eighttoeleven.dateandtime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DateAndTimeTest {

    @Test
    @DisplayName("60일 경과 여부를 판단한다.")
    void testIsAfter60Days() {
        //LocalDate
        LocalDate before61DaysFromNow = LocalDate.now().minusDays(61);
        LocalDate before60DaysFromNow = LocalDate.now().minusDays(60);
        LocalDate before59DaysFromNow = LocalDate.now().minusDays(59);

        assertThat(isAfter60Days(before61DaysFromNow)).isTrue();
        assertThat(isAfter60Days(before60DaysFromNow)).isFalse();
        assertThat(isAfter60Days(before59DaysFromNow)).isFalse();

        //String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String before61DaysFromNowString = before61DaysFromNow.format(formatter);
        String before60DaysFromNowString = before60DaysFromNow.format(formatter);
        String before59DaysFromNowString = before59DaysFromNow.format(formatter);
        assertThat(isAfter60Days(before61DaysFromNowString, formatter)).isTrue();
        assertThat(isAfter60Days(before60DaysFromNowString, formatter)).isFalse();
        assertThat(isAfter60Days(before59DaysFromNowString, formatter)).isFalse();
    }

    private boolean isAfter60Days(String date, DateTimeFormatter formatter) {
        LocalDate regDate = LocalDate.parse(date, formatter);
        return ChronoUnit.DAYS.between(regDate, LocalDate.now()) > 60;
    }

    private boolean isAfter60Days(LocalDate date) {
        return ChronoUnit.DAYS.between(date, LocalDate.now()) > 60;
    }

}