package cn.arorms.monitor.server.models;

import cn.arorms.monitor.server.enums.CriticalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "logs")
public class SystemLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname = "unknown";

    @Column(name = "cpu_usage")
    private double cpuUsage;

    @Column(name = "cpu_temperature")
    private double cpuTemperature;

    @Column(name = "memory_used")
    private long memoryUsed;

    @Column(name = "memory_usage")
    private double memoryUsage;

    @Enumerated(EnumType.STRING)
    private CriticalStatus status;

    private LocalDateTime timestamp = LocalDateTime.now();

    public SystemLog(String hostname, double cpuUsage, double cpuTemp, long usedMemoryMb, double memoryUsage,
                     CriticalStatus systemStatus, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.cpuTemperature = cpuTemp;
        this.memoryUsed = usedMemoryMb;
        this.memoryUsage = memoryUsage;
        this.status = systemStatus;
        this.timestamp = timestamp;
    }
}