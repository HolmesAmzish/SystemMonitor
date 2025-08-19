package cn.arorms.monitor.server.service;

import cn.arorms.monitor.server.model.SystemLog;
import cn.arorms.monitor.server.repository.SystemLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling system logs.
 * Provides methods to record and retrieve system logs.
 */
@Service
public class SystemLogService {
    private final SystemLogRepository systemLogRepository;

    public SystemLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    // Record a system log
    public void recordSystemLog(SystemLog log) {
        systemLogRepository.save(log);
    }

    // Pageable query
    public Page<SystemLog> getLogs(Pageable pageable) {
        return systemLogRepository.findAll(pageable);
    }
}
