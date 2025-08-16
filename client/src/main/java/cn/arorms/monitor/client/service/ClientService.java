package cn.arorms.monitor.client.service;

import cn.arorms.monitor.client.dtos.SystemLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientService {
    private final RestTemplate restTemplate;
    private final String serverBaseUrl;

    @Autowired
    public ClientService(RestTemplate restTemplate, @Value("${client.server-base-url}") String serverBaseUrl) {
        this.restTemplate = restTemplate;
        this.serverBaseUrl = serverBaseUrl;
    }

    public String getHostname() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            return "unknown";
        }
    }

    public void reportSystemInfo(SystemLogDto systemLog) {
        String url = serverBaseUrl + "/api/report";
        restTemplate.postForObject(url, systemLog, Void.class);
    }
}
