package me.jsj.thejava.tests.testtools.junit;

import me.jsj.thejava.tests.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatTest {

    /**
     * @param repetitionInfo - 현재 인덱스, 총 인덱스 수를 알려주는 파라미터 객체
     * @RepeatedTest(10) - 주어진 인자 수만큼 반복 실행
     */
    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo);
    }

    @ParameterizedTest
    @CsvSource(value = {"a:2", "b:4"}, delimiter = ':')
    void unitTest(String a, int i) {
        System.out.println("a = " + a);
        System.out.println("i = " + i);
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @ValueSource(strings = {"a", "b", "c", "d"})
//    @EmptySource //빈 값을 넣어준다.
//    @NullSource //Null 값을 넣어준다.
    @NullAndEmptySource //Null과 빈 값을 넣어준다.
    void valueSourceParameterizedTest(String s) {
        System.out.println("s = " + s);
    }

    /**
     * @ConvertWith
     * - 파라미터 테스트를 할 때 Primitive 값을 객체로 변환하고 싶으면 사용
     * - 별도의 컨버터를 구현해야 한다. (SimpleArgumentConverter 구현체 -> static class여야 한다.)
     */
    @DisplayName("파라미터 테스트 - 컨버터와 ValueSource")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedWithConverterTestV1(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    //하나의 Argument만 적용 가능
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can Only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    /**
     * ArgumentsAccessor
     * - 파라미터로 받은 후 Index를 통해 파라미터 접근 가능
     */
    @DisplayName("파라미터 테스트 - 컨버터 / CsvSource / ArgumentsAccessor")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @CsvSource(value = {"10, '자바 스프링'", "20, '스프링 데이터 JPA'", "30, '이펙티브 자바'"})
    void parameterizedWithConverterTestV2(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getString(1), argumentsAccessor.getInteger(0));
        System.out.println("study = " + study);
    }

    /**
     * @AggregateWith
     * - 여러 개의 Arguments를 적용하여 객체 생성 가능
     * - 별도의 컨버터 구현 필요 (ArgumentsAggregator 인터페이스 구현체 -> static class여야 한다.)
     */
    @DisplayName("파라미터 테스트 - 컨버터 / CsvSource / ArgumentsAggregator")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @CsvSource(value = {"10, '자바 스프링'", "20, '스프링 데이터 JPA'", "30, '이펙티브 자바'"})
    void parameterizedWithConverterTestV3(@AggregateWith(StudyConverterV2.class) Study study) {
        System.out.println("study = " + study);
    }

    static class StudyConverterV2 implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getString(1), accessor.getInteger(0));
        }
    }


}
