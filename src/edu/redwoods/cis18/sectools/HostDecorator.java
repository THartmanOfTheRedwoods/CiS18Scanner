package edu.redwoods.cis18.sectools;

import java.net.Inet4Address;
import java.net.Inet6Address;

public abstract class HostDecorator implements Host {
    protected Host host;

    public HostDecorator(Host host) {
        this.host = host;
    }

    @Override
    public void connect() {
        host.connect();
    }

    @Override
    public Inet4Address getIpv4Address() {
        return host.getIpv4Address();
    }

    @Override
    public void setIpv4Address(Inet4Address ipv4Address) {
        host.setIpv4Address(ipv4Address);
    }

    @Override
    public Inet6Address getIpv6Address() {
        return host.getIpv6Address();
    }

    @Override
    public void setIpv6Address(Inet6Address ipv6Address) {
        host.setIpv6Address(ipv6Address);
    }

    public String getDomainName() {
        return host.getDomainName();
    }

    @Override
    public void register(HostWatcher watcher) {
        host.register(watcher);
    }

    @Override
    public void notifyWatchers() {
        host.notifyWatchers();
    }

}
