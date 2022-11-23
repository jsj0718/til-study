package me.jsj.thejava.tests.testtools.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jsj.thejava.tests.domain.Study;
import me.jsj.thejava.tests.study.StudyApiController;
import me.jsj.thejava.tests.study.StudyController;
import me.jsj.thejava.tests.study.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {StudyController.class, StudyApiController.class})
@ExtendWith(MockitoExtension.class)
public class StudyControllerWebMvcTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudyRepository studyRepository;

    @BeforeEach
    void setup() {
        Study study = new Study("Java", 10);
        given(studyRepository.save(study))
                .willReturn(study);
        given(studyRepository.findById(1L))
                .willReturn(Optional.of(study));
        given(studyRepository.findByName("Java"))
                .willReturn(List.of(study));
    }

    @Test
    @DisplayName("스터디 폼 조회 - 성공")
    void newForm_success() throws Exception {
        mvc.perform(get("/study/new-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("study/new-form"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("limit"))
                .andExpect(model().attributeDoesNotExist("email"));
    }

    /**
     * - 요청
     *  - 단순 URL 요청
     * - 응답
     *  - Status 검증
     *  - Redirect 경로 및 반환 View 검증
     */
    @Test
    @DisplayName("스터디 폼 조회 - 리다이렉트")
    void newForm_redirect() throws Exception {
        mvc.perform(get("/study/redirect-new-form"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:study/new-form"))
                .andExpect(redirectedUrl("study/new-form"));
    }

    /**
     * - 요청
     *  - URL Query 파라미터 요청
     * - 응답
     *  - Status 검증
     *  - JSON API Response 검증
     */
    @Test
    @DisplayName("스터디 API 조회 : GET Param - 성공")
    void get_study_param_success() throws Exception {
        String name = "$.[?(@.name == '%s')]";
        String limit = "$.[?(@.limit == '%s')]";
        String status = "$.[?(@.status == '%s')]";

        mvc.perform(get("/study")
                        .param("name", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name, "Java").exists())
                .andExpect(jsonPath(limit, 10).exists())
                .andExpect(jsonPath(status, "DRAFT").exists());
    }

    /**
     * - 요청
     *  - PathVariable 요청
     * - 응답
     *  - Status 검증
     *  - JSON API Response 검증
     */
    @Test
    @DisplayName("스터디 API 조회 : GET PathVariable - 성공")
    void get_study_success() throws Exception {
        String name = "$.[?(@.name == '%s')]";
        String limit = "$.[?(@.limit == '%s')]";
        String status = "$.[?(@.status == '%s')]";

        mvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name, "Java").exists())
                .andExpect(jsonPath(limit, 10).exists())
                .andExpect(jsonPath(status, "DRAFT").exists());
    }

    /**
     * - 요청
     *  - HTTP FORM 형식으로 데이터 요청
     * - 응답
     *  - Status 검증
     *  - 텍스트 반환 검증
     */
    @Test
    @DisplayName("스터디 폼 데이터 생성 및 텍스트 반환 - 성공")
    void create_study_formdata_response_string_success() throws Exception {
        mvc.perform(post("/study-form-response-string")
                        .param("name", "Java")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateStudyRequestDto {
        private String name;
        private int limit;
    }

}
