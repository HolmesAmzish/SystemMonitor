package cn.arorms.systemmonitor;

import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemMonitoringImpl {
    private static final long STARTUP_TIME = ManagementFactory.getRuntimeMXBean().getStartTime();
    private static final Logger logger = Logger.getLogger(SystemMonitoringImpl.class.getName());

    private final OperatingSystemMXBean osBean;
    private final Runtime runtime;
    private final ThreadMXBean threadMXBean;
    private final GarbageCollectorMXBean gcBean;

    public SystemMonitoringImpl() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.runtime = Runtime.getRuntime();
        this.threadMXBean = ManagementFactory.getThreadMXBean();
        this.gcBean = ManagementFactory.getGarbageCollectorMXBeans().get(0);
    }

    public void checkSystemUsage(String serviceName) {
        logStart(serviceName);
        try {
            checkHeapMemoryUsage(serviceName);
            checkCpuUsage(serviceName);
            checkDiskUsage(serviceName);
            checkNetworkUsage(serviceName);
            checkThreadCount(serviceName);
            checkGCStats(serviceName);
        } catch (Exception exception) {
            logError("Error during system usage check", serviceName, exception);
        }
        logEnd(serviceName);
    }

    private void checkHeapMemoryUsage(String serviceName) {
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        logInfo(String.format("Heap Memory Usage: %dMB / %dMB", used / 1048576, total / 1048576), serviceName);
    }

    private void checkCpuUsage(String serviceName) {
        double load = osBean.getSystemLoadAverage();
        logInfo(String.format("CPU Load: %.2f", load), serviceName);
    }

    private void checkDiskUsage(String serviceName) {
        File root = new File("/");
        long total = root.getTotalSpace();
        long free = root.getFreeSpace();
        logInfo(String.format("Disk Usage: %dGB free of %dGB", free / 1073741824, total / 1073741824), serviceName);
    }

    private void checkNetworkUsage(String serviceName) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface net : Collections.list(networkInterfaces)) {
                logInfo(String.format("Network Interface: %s", net.getName()), serviceName);
            }
        } catch (SocketException e) {
            logError("Error retrieving network interfaces", serviceName, e);
        }
    }

    private void checkThreadCount(String serviceName) {
        int threadCount = threadMXBean.getThreadCount();
        logInfo(String.format("Active Threads: %d", threadCount), serviceName);
    }

    private void checkGCStats(String serviceName) {
        long gcCount = gcBean.getCollectionCount();
        logInfo(String.format("GC Collections: %d", gcCount), serviceName);
    }

    public void logStartupTime(String serviceName) {
        Instant startupInstant = Instant.ofEpochMilli(STARTUP_TIME);
        LocalDateTime startupTime = LocalDateTime.ofInstant(startupInstant, ZoneId.systemDefault());
        logInfo("Application Startup Time: " + startupTime, serviceName);
    }

    private void logInfo(String message, String serviceName) {
        logger.log(Level.INFO, "[{0}] {1}", new Object[]{serviceName, message});
    }

    private void logError(String message, String serviceName, Exception exception) {
        logger.log(Level.SEVERE, "[{0}] {1} - {2}", new Object[]{serviceName, message, exception.getMessage()});
    }

    private void logStart(String serviceName) {
        logger.log(Level.INFO, "🔹 [{0}] Monitoring started", new Object[]{serviceName});
    }

    private void logEnd(String serviceName) {
        logger.log(Level.INFO, "✅ [{0}] Monitoring completed", new Object[]{serviceName});
    }
}