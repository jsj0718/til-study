package me.jsj.resttemplate;

import lombok.extern.slf4j.Slf4j;
import me.jsj.resttemplate.util.Signatures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void keywordsTest() throws Exception {
        log.info("ACCESS_LIC = {}", ACCESS_LIC);
        log.info("SECRET_KEY = {}", SECRET_KEY);
        log.info("CUSTOMER_ID = {}", CUSTOMER_ID);

        //given
        String baseUrl = "https://api.searchad.naver.com";
        String resource = "/ncc/keywords";

//        List<String> keywords = List.of("nkw-a001-01-000004906631873", "nkw-a001-01-000004996203317");
        List<String> keywords = List.of("nkw-a001-01-000001187910673");

        String timestamp = String.valueOf(System.currentTimeMillis());

        String signature = Signatures.of(timestamp, HttpMethod.GET.name(), resource, SECRET_KEY);

        log.info("signature = {}", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-Timestamp", timestamp);
        headers.set("X-API-KEY", ACCESS_LIC);
        headers.set("X-Customer", CUSTOMER_ID);
        headers.set("X-Signature", signature);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path(resource)
                .queryParam("ids", keywords)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        System.out.println("requestEntity = " + requestEntity);

        //when
        ResponseEntity<Object> response = restTemplate.exchange(requestEntity, Object.class);

        //then
        assertThat(response).isNotNull();

        System.out.println("response = " + response);
    }

    @Test
    void adgroupTest() throws Exception {
        log.info("ACCESS_LIC = {}", ACCESS_LIC);
        log.info("SECRET_KEY = {}", SECRET_KEY);
        log.info("CUSTOMER_ID = {}", CUSTOMER_ID);

        //given
        String baseUrl = "https://api.searchad.naver.com";
        String resource = "/ncc/adgroups";
        List<String> keywords = List.of("grp-a001-01-000000030200296", "grp-a001-01-000000023716793");

        String timestamp = String.valueOf(System.currentTimeMillis());

        String signature = Signatures.of(timestamp, HttpMethod.GET.name(), resource, SECRET_KEY);

        log.info("signature = {}", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-Timestamp", timestamp);
        headers.set("X-API-KEY", ACCESS_LIC);
        headers.set("X-Customer", CUSTOMER_ID);
        headers.set("X-Signature", signature);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path(resource)
                .queryParam("ids", keywords)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        System.out.println("requestEntity = " + requestEntity);

        //when
        ResponseEntity<Object> response = restTemplate.exchange(requestEntity, Object.class);

        //then
        assertThat(response).isNotNull();

        System.out.println("response = " + response);
    }
}