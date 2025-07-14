package cn.arorms.systemmonitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemMonitorApplicationTests {

    @Autowired
    private SystemMonitorService systemMonitorService;

    @Test
    void contextLoads() {
        systemMonitorService.checkSystemHealth();
    }
}
