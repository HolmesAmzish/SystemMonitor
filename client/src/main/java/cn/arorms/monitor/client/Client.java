package cn.arorms.monitor.client;

import cn.arorms.monitor.client.dtos.SystemLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Client {
    private final RestTemplate restTemplate;
    private final String serverBaseUrl;

    @Autowired
    public Client(RestTemplate restTemplate, @Value("${client.server-base-url}") String serverBaseUrl) {
        this.restTemplate = restTemplate;
        this.serverBaseUrl = serverBaseUrl;
    }

    public void reportSystemInfo(SystemLogDto systemLog) {
        String url = serverBaseUrl + "/api/report";
        restTemplate.postForObject(url, systemLog, Void.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

