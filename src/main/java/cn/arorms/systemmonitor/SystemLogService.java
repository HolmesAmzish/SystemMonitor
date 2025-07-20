package cn.arorms.systemmonitor;

import org.springframework.stereotype.Service;

@Service
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;

    public SystemLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public void saveSystemLog(SystemLogEntity log) {
        systemLogRepository.save(log);
    }
}
