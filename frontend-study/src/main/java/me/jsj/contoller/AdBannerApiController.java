package me.jsj.contoller;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;

@RestController
public class AdBannerApiController {

    @GetMapping("/api/ad-banner")
    public ResponseDto apiAdBanner() {
        String url = "https://alpha.display.sellerad.net/adReq?query=청바지&channelId=D01P02101&uid=1&userIp=49.154.179.208&adInfoType=ADD";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseDto responseDto = restTemplate.exchange(url, HttpMethod.GET, entity, ResponseDto.class).getBody();
        System.out.println("responseDto = " + responseDto);

        return responseDto;
    }

    @Data
    static class ResponseDto implements Serializable {
        private String requestId;
        private String resultCode;
        private String channelId;
        private String adCnt;
        private List<ADS> ads;
        private String query;
        private String timeStamp;

    }
    @Data
    static class ADS implements Serializable {
        private String adRank;
        private String itemNo;
        private String itemName;
        private String itemUrl;
        private String imgUrl;
        private String itemPcCost;
        private String itemMobileCost;
        private String clickUrl;
        private boolean cenBfYn;
    }

}
