package me.jsj.thejava.tests.testtools.unit;

import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UnitJsonTest {
    @Autowired
    JacksonTester<Study> json;

    @Test
    void 직렬화_테스트() throws Exception {
        Study study = new Study("Java", 10);

        assertThat(json.write(study)).hasJsonPathStringValue("@.name");
        assertThat(json.write(study)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Java");
        assertThat(json.write(study)).extractingJsonPathNumberValue("@.limit")
                .isEqualTo(10);
    }

    @Test
    void 역직렬화_테스트() throws Exception {
        String content = "{\"name\": \"Java\", \"limit\": \"10\"}";
        assertThat(json.parseObject(content).getName()).isEqualTo("Java");
        assertThat(json.parseObject(content).getLimit()).isEqualTo(10);
        assertThat(json.parseObject(content).getStatus()).isEqualTo(StudyStatus.DRAFT);
    }
}
