package cn.arorms.monitor.server.controller;

import cn.arorms.monitor.server.models.SystemLog;
import cn.arorms.monitor.server.models.SystemStatus;
import cn.arorms.monitor.server.service.SystemStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SystemStatusController {

    private final SystemStatusService systemStatusService;

    public SystemStatusController(SystemStatusService systemStatusService) {
        this.systemStatusService = systemStatusService;
    }

    @PostMapping("/api/status/addDevice")
    public void addDevice(String hostname) {
        systemStatusService.addDevice(hostname);
    }

    @GetMapping("/api/status")
    public List<SystemStatus> GetAllSystemStatus() {
        return systemStatusService.getAllSystemStatus();
    }
}
