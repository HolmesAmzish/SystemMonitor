package cn.arorms.monitor.server.service;

import cn.arorms.monitor.server.models.SystemLog;
import cn.arorms.monitor.server.models.SystemStatus;
import cn.arorms.monitor.server.repository.SystemStatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemStatusService {

    private final SystemStatusRepository systemStatusRepository;

    public SystemStatusService(SystemStatusRepository systemStatusRepository) {
        this.systemStatusRepository = systemStatusRepository;
    }

    public void addDevice(String hostname) {
        SystemStatus status = new SystemStatus();
        status.setHostname(hostname);
        systemStatusRepository.save(status);
    }

    public void updateSystemStatus(SystemLog systemLog) {
        systemStatusRepository.findById(systemLog.getHostname())
                .ifPresent(status -> {
                    status.setCpuUsage(systemLog.getCpuUsage());
                    status.setCpuTemperature(systemLog.getCpuTemperature());
                    status.setMemoryUsed(systemLog.getMemoryUsed());
                    status.setMemoryUsage(systemLog.getMemoryUsage());
                    status.setStatus(systemLog.getStatus().toString());
                    status.setLastUpdated(systemLog.getTimestamp());
                    systemStatusRepository.save(status);
                });
    }


    public List<SystemStatus> getAllSystemStatus() {
        Iterable<SystemStatus> iterable = systemStatusRepository.findAll();
        List<SystemStatus> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
