package cn.arorms.systemmonitor;

import java.net.InetAddress;

public class HostnameTest {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostname = inetAddress.getHostName();
            System.out.println("Hostname: " + hostname);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
