package cn.arorms.systemmonitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvironmentTest {
    @Autowired
    SystemMonitorService systemMonitorService;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;

    @Test
    public void contextLoads() {
        System.out.println("Email password: " + emailPassword);
    }
}
