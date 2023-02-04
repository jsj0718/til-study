package me.jsj.thejava.tests.testtools.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jsj.thejava.tests.testtools.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
public class StudyControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * - 요청
     *  - 단순 URL 요청
     * - 응답
     *  - Status 검증
     *  - View & Model 검증
     */
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
    @DisplayName("스터디 API 조회 : GET Param V1 - 성공")
    void get_study_param_successV1() throws Exception {
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

    @Test
    @DisplayName("스터디 API 조회 : GET Param V2 - 성공")
    void get_study_param_successV2() throws Exception {
        mvc.perform(get("/study")
                        .param("name", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.limit").value(10))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    /**
     * - 요청
     *  - PathVariable 요청
     * - 응답
     *  - Status 검증
     *  - JSON API Response 검증
     */
    @Test
    @DisplayName("스터디 API 조회 : GET PathVariable V1 - 성공")
    void get_study_successV1() throws Exception {
        String name = "$.[?(@.name == '%s')]";
        String limit = "$.[?(@.limit == '%s')]";
        String status = "$.[?(@.status == '%s')]";

        mvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name, "Java").exists())
                .andExpect(jsonPath(limit, 10).exists())
                .andExpect(jsonPath(status, "DRAFT").exists());
    }

    @Test
    @DisplayName("스터디 API 조회 : GET PathVariable V2 - 성공")
    void get_study_successV2() throws Exception {
        mvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.limit").value(10))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    /**
     * - 요청
     *  - HTTP FORM 형식으로 데이터 요청
     * - 응답
     *  - Status 검증
     *  - JSON API Response 검증
     */
    @Test
    @DisplayName("스터디 폼 데이터 생성 V1 - 성공")
    void create_study_formdata_successV1() throws Exception {
        String name = "$.[?(@.name == '%s')]";
        String limit = "$.[?(@.limit == '%s')]";
        String status = "$.[?(@.status == '%s')]";

        mvc.perform(post("/study-form")
                        .param("name", "더 자바, 테스트")
                        .param("limit", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name, "더 자바, 테스트").exists())
                .andExpect(jsonPath(limit, 100).exists())
                .andExpect(jsonPath(status, "DRAFT").exists());
    }

    @Test
    @DisplayName("스터디 폼 데이터 생성 V2 - 성공")
    void create_study_formdata_successV2() throws Exception {
        mvc.perform(post("/study-form")
                        .param("name", "더 자바, 테스트")
                        .param("limit", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("더 자바, 테스트"))
                .andExpect(jsonPath("$.limit").value(100))
                .andExpect(jsonPath("$.status").value("DRAFT"));
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
                        .param("name", "더 자바, 테스트")
                        .param("limit", "100"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    /**
     * - 요청
     *  - Http Body에 JSON 형태로 요청
     * - 응답
     *  - Status 검증
     *  - JSON API Response 검증
     */
    @Test
    @DisplayName("스터디 API 생성 V1 - 성공")
    void create_study_success() throws Exception {
        String name = "$.[?(@.name == '%s')]";
        String limit = "$.[?(@.limit == '%s')]";
        String status = "$.[?(@.status == '%s')]";

        CreateStudyRequestDto dto = new CreateStudyRequestDto("더 자바, 테스트", 100);
        mvc.perform(post("/study")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name, "더 자바, 테스트").exists())
                .andExpect(jsonPath(limit, 100).exists())
                .andExpect(jsonPath(status, "DRAFT").exists());
    }

    @Test
    @DisplayName("스터디 API 생성 V2 - 성공")
    void create_study_successV2() throws Exception {
        CreateStudyRequestDto dto = new CreateStudyRequestDto("더 자바, 테스트", 100);
        mvc.perform(post("/study")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("더 자바, 테스트"))
                .andExpect(jsonPath("$.limit").value(100))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateStudyRequestDto {
        private String name;
        private int limit;
    }
}
