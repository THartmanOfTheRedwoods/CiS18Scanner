package edu.redwoods.cis18.sectools;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public abstract class HostFactory {

    public static Host getDecoratedHost(String[] decorators, InetAddress address, boolean isIPv4) {
        Host h = getHost("Basic", address, isIPv4);
        for (String decorator : decorators) {
            h = decorateHost(decorator, h);
        }
        return h;
    }

    public static Host decorateHost(String hostType, Host host) {
        return switch (hostType) {
            case "DNS" -> new DNSDecorator(host);
            case "Ping" -> new PingDecorator(host);
            case "PortScan" -> new PortScanDecorator(host);
            default -> null;
        };
    }

    public static Host getHost(String hostType, InetAddress address, boolean isIPv4) {
        return switch (hostType) {
            case "Basic" -> (isIPv4) ? new BasicHost((Inet4Address) address) : new BasicHost((Inet6Address) address);
            case "DNS" -> new DNSDecorator(getHost("Basic", address, isIPv4));
            case "Ping" -> new PingDecorator(getHost("Basic", address, isIPv4));
            case "PortScan" -> new PortScanDecorator(getHost("Basic", address, isIPv4));
            default -> null;
        };
    }
}
