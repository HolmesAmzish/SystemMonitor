package cn.arorms.monitor.server.repository;

import cn.arorms.monitor.server.models.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
}
