package cn.arorms.systemmonitor;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.Sensors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SystemMonitorService {

    private final SystemInfo systemInfo = new SystemInfo();
    private final MailService mailService;

    @Value("${monitor.temp-threshold-celsius}")
    private double tempThreshold;

    @Value("${monitor.email-to}")
    private String alertEmail;

    private long[] prevTicks = systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks();

    public SystemMonitorService(MailService mailService) {
        this.mailService = mailService;
    }

//    @Scheduled(fixedRate = 1800000) // 每30分钟检查一次
    public void checkSystemHealth() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        Sensors sensors = systemInfo.getHardware().getSensors();

        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks);
        prevTicks = processor.getSystemCpuLoadTicks();

        double cpuTemp = sensors.getCpuTemperature();

        long usedMemoryMb = (memory.getTotal() - memory.getAvailable()) / (1024 * 1024);

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String statusReport = String.format(
                "[%s] 系统资源状态报告:\n" +
                        "CPU使用率: %.2f%%\n" +
                        "CPU温度: %.1f°C\n" +
                        "内存使用量: %d MB\n",
                timestamp, cpuLoad * 100, cpuTemp, usedMemoryMb);

        System.out.println(statusReport);

        mailService.sendMail(alertEmail, "服务器资源状态报告", statusReport);

        if (cpuTemp > tempThreshold) {
            String alertMsg = String.format("[%s] 服务器温度告警:\nCPU温度过高: %.1f°C (阈值: %.1f°C)\n请及时检查服务器散热！",
                    timestamp, cpuTemp, tempThreshold);
            mailService.sendMail(alertEmail, "服务器CPU温度告警", alertMsg);
        }
    }

}
