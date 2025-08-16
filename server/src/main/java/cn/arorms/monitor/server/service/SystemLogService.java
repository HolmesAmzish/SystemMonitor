package cn.arorms.monitor.server.service;

import cn.arorms.monitor.server.model.SystemLog;
import cn.arorms.monitor.server.repository.SystemLogRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemLogService {
    private final SystemLogRepository systemLogRepository;

    public SystemLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public void recordSystemLog(SystemLog log) {
        systemLogRepository.save(log);
    }
}
