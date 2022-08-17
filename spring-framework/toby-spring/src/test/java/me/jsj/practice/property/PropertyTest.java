package me.jsj.practice.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "springBootTest = a")
@TestPropertySource(locations = "classpath:application-proptest.properties")
@ActiveProfiles("test")
public class PropertyTest {

    @Value("${spring.h2.console.path}")
    String h2Path;

    @Value("${springBootTest}")
    String springBootTestValue;

    @Value("${testPropertySource}")
    String testPropertySourceValue;

    @Value("${activeProfiles}")
    String activeProfilesValue;

    @Test
    void testProperties() {
        assertThat(h2Path).isEqualTo("/h2-console");
        assertThat(springBootTestValue).isEqualTo("a");
        assertThat(testPropertySourceValue).isEqualTo("b");
        assertThat(activeProfilesValue).isEqualTo("c");
    }
}
