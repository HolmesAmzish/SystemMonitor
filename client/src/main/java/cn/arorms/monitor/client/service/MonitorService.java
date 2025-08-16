package cn.arorms.monitor.client.service;
import cn.arorms.monitor.client.dtos.SystemLogDto;

import java.net.InetAddress;
import java.time.LocalDateTime;

import cn.arorms.monitor.client.enums.SystemStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.Sensors;


@Service @Slf4j @EnableScheduling
public class MonitorService {

    private final ClientService clientService;

    public MonitorService(ClientService clientService) {
        this.clientService = clientService;
    }

    private final long recordInterval = 1800 * 1000;
    private  float cpuAlertTemperature = 55.0f;

    private final SystemInfo systemInfo = new SystemInfo();

    @Scheduled(fixedRate = recordInterval)
    public void reportSystemHealth() {

        // Set default system status
        SystemStatus systemStatus = SystemStatus.NORMAL;

        // Hostname
        String hostname = clientService.getHostname();

        // CPU Usage
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        double cpuUsage = processor.getSystemCpuLoad(1000);
//        log.info("CPU Usage: {}%", cpuUsage);

        // CPU Temperature
        Sensors sensors = systemInfo.getHardware().getSensors();
        double cpuTemp = sensors.getCpuTemperature();
        if (cpuTemp > cpuAlertTemperature) {
            systemStatus = SystemStatus.WARNING;
        }

        // Memory Usage
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long usedMemoryMb = (memory.getTotal() - memory.getAvailable()) / (1024 * 1024);
        double memoryUsage = 1 - ((double)memory.getAvailable() / memory.getTotal());

        // Timestamp
        LocalDateTime timestamp = LocalDateTime.now();

        SystemLogDto systemLog = new SystemLogDto(
                hostname, cpuUsage, cpuTemp, usedMemoryMb, memoryUsage, systemStatus, timestamp
        );

        log.info("Reporting system health: {}", systemLog.toString());
        clientService.reportSystemInfo(systemLog);
    }
}
