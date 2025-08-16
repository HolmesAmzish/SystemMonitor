package cn.arorms.monitor.server.controller;

import cn.arorms.monitor.server.model.SystemLog;
import cn.arorms.monitor.server.service.SystemLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemLogController {
    private final SystemLogService systemLogService;
    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @PostMapping("/api/report")
    public void reportSystemLog(@RequestBody SystemLog systemLogDto) {
        // Convert DTO to entity if necessary
        SystemLog systemLog = new SystemLog();
        systemLog.setHostname(systemLogDto.getHostname());
        systemLog.setCpuUsage(systemLogDto.getCpuUsage());
        systemLog.setCpuTemperature(systemLogDto.getCpuTemperature());
        systemLog.setMemoryUsed(systemLogDto.getMemoryUsed());
        systemLog.setTimestamp(systemLogDto.getTimestamp());
        systemLog.setMemoryUsage(systemLogDto.getMemoryUsage());
        systemLog.setStatus(systemLogDto.getStatus());

        // Save the log using the service
        systemLogService.recordSystemLog(systemLog);
    }
}
