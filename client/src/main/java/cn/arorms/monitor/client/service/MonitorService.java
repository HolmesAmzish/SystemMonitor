package cn.arorms.monitor.client.service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.Sensors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MonitorService {

    private final SystemInfo systemInfo = new SystemInfo();

    private long[] prevTicks = systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks();

    @Value("${monitor.cpu-alert-temperature}")
    private float cpuAlertTemperature;
    @Value("${monitor.master-email}")
    private String masterEmail;

    private final long RECORD_RATE = 1800000;

    @Scheduled(fixedRate = RECORD_RATE)
    public void checkSystemHealth() {
        String hostname;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostname = inetAddress.getHostName();
        } catch (Exception e) {
            hostname = "unknown";
        }

        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        Sensors sensors = systemInfo.getHardware().getSensors();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks);
        prevTicks = processor.getSystemCpuLoadTicks();
        double cpuTemp = sensors.getCpuTemperature();
        long usedMemoryMb = (memory.getTotal() - memory.getAvailable()) / (1024 * 1024);
        LocalDateTime timestamp = LocalDateTime.now();

        SystemStatus systemStatus = SystemStatus.NORMAL;
        SystemLogEntity log = new SystemLogEntity(hostname, cpuLoad, cpuTemp, usedMemoryMb, systemStatus, timestamp);
        systemLogService.saveSystemLog(log);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestampString = timestamp.format(formatter);



        mailService.sendMail(masterEmail, "服务器资源状态报告", statusReport);

        if (cpuTemp > cpuAlertTemperature) {
            String alertMsg = String.format("[%s] 服务器温度告警:\nCPU温度过高: %.1f°C (阈值: %.1f°C)\n请及时检查服务器散热！",
                    timestampString, cpuTemp, cpuAlertTemperature);
            mailService.sendMail(masterEmail, "服务器CPU温度告警", alertMsg);
        }
    }

}