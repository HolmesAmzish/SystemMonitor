package cn.arorms.systemmonitor;

public class MonitoringTest {
    public static void main(String[] args) {
        SystemMonitoringImpl systemMonitoring = new SystemMonitoringImpl();
        systemMonitoring.logStartupTime("TestService");
        while (true) {
            systemMonitoring.checkSystemUsage("TestService");
            try {
                Thread.sleep(5000); // Sleep for 60 seconds before the next check
            } catch (InterruptedException e) {
                System.err.println("Monitoring interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
