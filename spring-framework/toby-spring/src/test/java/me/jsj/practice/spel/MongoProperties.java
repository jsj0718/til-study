package me.jsj.practice.spel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Component
public class MongoProperties {
    private String host;
    private int port;
    private String uri;
}