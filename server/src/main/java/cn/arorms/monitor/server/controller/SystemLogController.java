package cn.arorms.monitor.server.controller;

import cn.arorms.monitor.server.models.SystemLog;
import cn.arorms.monitor.server.service.SystemLogService;
import cn.arorms.monitor.server.service.SystemStatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling system log requests.
 * Provides endpoints to report system logs and retrieve them.
 * @author Cacciatore
 * @version 1.0 2025-08-18
 */
@RestController
public class SystemLogController {
    private final SystemLogService systemLogService;
    private final SystemStatusService systemStatusService;

    public SystemLogController(SystemLogService systemLogService, SystemStatusService systemStatusService) {
        this.systemLogService = systemLogService;
        this.systemStatusService = systemStatusService;
    }

    @PostMapping("/api/report")
    public void reportSystemLog(@RequestBody SystemLog systemLog) {
        systemStatusService.updateSystemStatus(systemLog);
        systemLogService.recordSystemLog(systemLog);
    }

    @GetMapping("/api/logs")
    public Page<SystemLog> getSystemLogs(
            @PageableDefault(size = 20, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return systemLogService.getLogs(pageable);
    }
}

