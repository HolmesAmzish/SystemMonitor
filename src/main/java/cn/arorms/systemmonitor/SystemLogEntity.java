package cn.arorms.systemmonitor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "system_logs")
public class SystemLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String hostname = "unknown";

    private double cpuUsage;
    private double cpuTemperature;
    private long memoryUsage;

    @Enumerated(EnumType.STRING)
    private SystemStatus status;

    private LocalDateTime timestamp = LocalDateTime.now();

    public SystemLogEntity(String hostname, double cpuUsage, double cpuTemp, long usedMemoryMb, SystemStatus systemStatus, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.cpuTemperature = cpuTemp;
        this.memoryUsage = usedMemoryMb;
        this.status = systemStatus;
        this.timestamp = timestamp;
    }
}
