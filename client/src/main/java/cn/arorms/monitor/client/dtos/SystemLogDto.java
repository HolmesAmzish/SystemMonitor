package cn.arorms.monitor.client.dtos;

import cn.arorms.monitor.client.enums.CriticalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class SystemLogDto {
    private Long id;

    private String hostname = "unknown";

    private double cpuUsage;
    private double cpuTemperature;
    private long memoryUsed;
    private double memoryUsage;

    private CriticalStatus status;

    private LocalDateTime timestamp = LocalDateTime.now();

    public SystemLogDto(String hostname, double cpuUsage, double cpuTemp, long usedMemoryMb, double memoryUsage,
                        CriticalStatus systemStatus, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.cpuTemperature = cpuTemp;
        this.memoryUsed = usedMemoryMb;
        this.memoryUsage = memoryUsage;
        this.status = systemStatus;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SystemLogDto{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", cpuTemperature=" + cpuTemperature +
                ", memoryUsed=" + memoryUsed +
                ", memoryUsage=" + memoryUsage +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
