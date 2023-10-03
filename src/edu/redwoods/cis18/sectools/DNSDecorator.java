package edu.redwoods.cis18.sectools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSDecorator extends HostDecorator {
    private String domainName;
    public DNSDecorator(Host host) {
        super(host);
    }

    @Override
    public void connect() {
        super.connect();
        addDNSResolution();
    }

    private void addDNSResolution() {
        if(!(this.domainName == null || this.domainName.isEmpty())) { // No need to resolve again since we've already resolved.
            return;
        }
        System.out.println("Adding DNS resolution capability...");

        // Resolve the IPv4 or IPv6 address based on availability
        if (host.getIpv4Address() != null) {
            try {
                String ipv4 = host.getIpv4Address().getHostAddress();
                InetAddress resolvedAddress = InetAddress.getByName(ipv4);
                domainName = getReverseDNS(resolvedAddress);
                System.out.println("Resolved IPv4 Address: " + domainName);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else if (host.getIpv6Address() != null) {
            try {
                String ipv6 = host.getIpv6Address().getHostAddress();
                InetAddress resolvedAddress = InetAddress.getByName(ipv6);

                domainName = getReverseDNS(resolvedAddress);
                System.out.println("Resolved IPv6 Address: " + domainName);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No IP address available for DNS resolution.");
        }
    }

    private String getReverseDNS(InetAddress address) {
        try {
            return InetAddress.getByName(address.getHostAddress()).getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown Host";
        }
    }

    @Override
    public String getDomainName() {
        if( this.domainName == null ) {
            this.addDNSResolution();
        }
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}