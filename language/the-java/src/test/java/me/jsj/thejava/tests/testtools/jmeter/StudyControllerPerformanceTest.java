package me.jsj.thejava.tests.testtools.jmeter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyRepository;
import me.jsj.thejava.tests.testtools.infra.MockMvcTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class StudyControllerPerformanceTest {
    static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudyRepository studyRepository;

    @BeforeEach
    void setup() {
        Study study = new Study("이펙티브 자바", 20);
        studyRepository.save(study);
    }

    @AfterEach
    void clean() {
        studyRepository.deleteAll();
    }

    @Test
    void create_study() throws Exception {
        CreateStudyRequestDto dto = new CreateStudyRequestDto("더 자바, 테스트", 10);
        mockMvc.perform(post("/study")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void get_study() throws Exception {
        mockMvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateStudyRequestDto {
        private String name;
        private int limit;
    }
}
