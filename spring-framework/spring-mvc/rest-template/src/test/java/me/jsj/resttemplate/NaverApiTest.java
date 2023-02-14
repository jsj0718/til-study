package me.jsj.resttemplate;

import lombok.extern.slf4j.Slf4j;
import me.jsj.resttemplate.util.Signatures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class NaverApiTest {

    @Value("${naver.access_lic}")
    String ACCESS_LIC;

    @Value("${naver.secret_key}")
    String SECRET_KEY;

    @Value("${naver.customer_id}")
    String CUSTOMER_ID;

    @Autowired
    MockMvc mvc;

    @Test
    void apiTest() throws Exception {
        log.info("ACCESS_LIC = {}", ACCESS_LIC);
        log.info("SECRET_KEY = {}", SECRET_KEY);
        log.info("CUSTOMER_ID = {}", CUSTOMER_ID);

        //given
        String baseUrl = "https://api.searchad.naver.com";
        String resource = "/ncc/keywords";
        String queryParam = "?ids=nkw-a001-01-000000892471089";
        String keyword = "nkw-a001-01-000000892471089";

        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = Signatures.of(timestamp, "GET", resource, SECRET_KEY);

        log.info("signature = {}", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", timestamp);
        headers.set("X-API-KEY", ACCESS_LIC);
        headers.set("X-Customer", CUSTOMER_ID);
        headers.set("X-Signature", signature);
        headers.set("Content-Type", "application/json");

        //when
        String url = baseUrl + resource;
        MvcResult response = mvc.perform(get(url + "/{nccKeywordId}", keyword)
                        .headers(headers))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        assertThat(response).isNotNull();
    }
}