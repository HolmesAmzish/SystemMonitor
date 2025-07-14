package cn.arorms.systemmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SystemMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemMonitorApplication.class, args);
    }

}
