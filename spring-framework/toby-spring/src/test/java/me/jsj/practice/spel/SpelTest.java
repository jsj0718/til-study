package me.jsj.practice.spel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SpelTest {

    //expressions
    @Value("#{1 + 1}")
    int value;
    @Value("#{'sejin' + ' ' + 'jeong'}")
    String name;
    @Value("#{1 + 1 eq 2}")
    boolean equals;

    //references
    @Value("${foo.bar}")
    int bar;
    @Value("${foo.baz}")
    String baz;
    @Value("${foo.qux}")
    boolean qux;
    @Value("${foo.quux:default value}")
    String quux;

    //expressions + references
    @Value("#{${foo.quux:'Default Value'}.replace(' ', '')}")
    String upgradeQuux;

    //bean
    @Value("#{properties.number}")
    int number;

    @Autowired
    MongoProperties moduleProperties;

    @Test
    void testSpel() {
        //expressions
        assertThat(value).isEqualTo(2);
        assertThat(name).isEqualTo("sejin jeong");
        assertThat(equals).isTrue();

        //references
        assertThat(bar).isEqualTo(100);
        assertThat(baz).isEqualTo("test");
        assertThat(qux).isTrue();
        assertThat(quux).isEqualTo("default value");

        //expressions + references
        assertThat(upgradeQuux).isEqualTo("DefaultValue");

        //bean
        assertThat(number).isEqualTo(1);

        //configureProperty
        assertThat(moduleProperties.getHost()).isEqualTo("host");
        assertThat(moduleProperties.getPort()).isEqualTo(3400);
        assertThat(moduleProperties.getUri()).isEqualTo("uri");
    }
}
