package edu.redwoods.cis18.sectools;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;

public class BasicHost implements Host {
    private Inet4Address ipv4Address;
    private Inet6Address ipv6Address;
    private List<HostWatcher> watchers;

    // Constructor for IPv4 only
    public BasicHost(Inet4Address ipv4) {
        this(ipv4, null);
    }

    // Constructor for IPv6 only
    public BasicHost(Inet6Address ipv6) {
        this(null, ipv6);
    }

    // Constructor for both IPv4 and IPv6
    public BasicHost(Inet4Address ipv4, Inet6Address ipv6) {
        this.ipv4Address = ipv4;
        this.ipv6Address = ipv6;
        this.watchers = new ArrayList<>();
    }

    @Override
    public void connect() {
        System.out.println("Connecting to the basic host...");
        System.out.println("IPv4 Address: " + ipv4Address);
        System.out.println("IPv6 Address: " + ipv6Address);
    }

    // Getters and setters for IPv4 and IPv6 addresses
    @Override
    public Inet4Address getIpv4Address() {
        return ipv4Address;
    }

    @Override
    public void setIpv4Address(Inet4Address ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    @Override
    public Inet6Address getIpv6Address() {
        return ipv6Address;
    }

    @Override
    public void setIpv6Address(Inet6Address ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    @Override
    public String getDomainName() {
        if (this.getIpv4Address() != null) {
            return this.getIpv4Address().getHostAddress();
        } else if (this.getIpv6Address() != null) {
            return this.getIpv6Address().getHostAddress();
        }
        return null;
    }

    @Override
    public void register(HostWatcher watcher) {
        this.watchers.add(watcher);
    }

    @Override
    public void notifyWatchers() {
        for(HostWatcher hw : watchers) {
            hw.update();
        }
    }
}