package cn.arorms.monitor.server.controller;

import cn.arorms.monitor.server.model.SystemLog;
import cn.arorms.monitor.server.service.SystemLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling system log requests.
 * Provides endpoints to report system logs and retrieve them.
 * @author Cacciatore
 * @version 1.0 2025-08-18
 */
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

    @GetMapping("/api/logs")
    public Page<SystemLog> getSystemLogs(
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return systemLogService.getLogs(pageable);
    }
}
