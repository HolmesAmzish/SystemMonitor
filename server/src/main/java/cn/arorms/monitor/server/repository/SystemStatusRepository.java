package cn.arorms.monitor.server.repository;

import cn.arorms.monitor.server.models.SystemStatus;
import org.springframework.data.repository.CrudRepository;

public interface SystemStatusRepository extends CrudRepository<SystemStatus, String> {
    // This interface will inherit methods for CRUD operations from CrudRepository
    // Additional custom query methods can be defined here if needed
}
