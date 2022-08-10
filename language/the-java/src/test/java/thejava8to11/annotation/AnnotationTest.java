package thejava8to11.annotation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Chicken("양념")
@Chicken("간장")
public class AnnotationTest {

    @Test
    void testAnnotation() {
        List<@Chicken String> name = Arrays.asList("jsj");
    }

    //Chicken Annotation의 Target이 TYPE_PARAMETER이기 때문에 가능
    static class FeelsLikeChicken<@Chicken T> {

        //<C>: 타입 파라미터, C: 타입
        public static <@Chicken C> void print(@Chicken C c) {

        }
    }

    @Test
    void testAnnotationValues() {
        Chicken[] chickens = AnnotationTest.class.getAnnotationsByType(Chicken.class);

        assertThat(chickens[0].value()).isEqualTo("양념");
        assertThat(chickens[1].value()).isEqualTo("간장");
    }

    @Test
    void testAnnotationValuesUsingContainer() {
        ChickenContainer chickenContainer = AnnotationTest.class.getAnnotation(ChickenContainer.class);
        Chicken[] chickens = chickenContainer.value();

        assertThat(chickens[0].value()).isEqualTo("양념");
        assertThat(chickens[1].value()).isEqualTo("간장");
    }

}
