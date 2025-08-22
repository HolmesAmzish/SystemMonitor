package cn.arorms.monitor.server.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("system_status")
@Getter
@Setter
public class SystemStatus implements Serializable {

    @Id
    private String hostname; // unique identifier

    private double cpuUsage;
    private double cpuTemperature;
    private long memoryUsed;
    private double memoryUsage;
    private String status;
    private LocalDateTime lastUpdated;
}
