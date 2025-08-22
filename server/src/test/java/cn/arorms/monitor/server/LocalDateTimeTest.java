package cn.arorms.monitor.server;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LocalDateTimeTest {
    @Test
    public void localDateTimeTest() {
        var localDateTime = LocalDateTime.now();
        System.out.println("Current LocalDateTime: " + localDateTime);
    }
}
