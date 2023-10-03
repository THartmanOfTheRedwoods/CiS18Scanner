package edu.redwoods.cis18.sectools;

import java.net.Inet4Address;
import java.net.Inet6Address;

public interface Host {
    void connect();
    Inet4Address getIpv4Address();

    void setIpv4Address(Inet4Address ipv4Address);

    Inet6Address getIpv6Address();

    void setIpv6Address(Inet6Address ipv6Address);

    String getDomainName();

    void register(HostWatcher watcher);
    void notifyWatchers();
}
