import edu.redwoods.cis18.sectools.Host;
import edu.redwoods.cis18.sectools.HostFactory;
import edu.redwoods.cis18.sectools.HostWatcher;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        try {
            // Create a BasicHost with an IPv4 address
            Inet4Address ipv4Address = (Inet4Address) Inet4Address.getByName("142.250.189.164");

            //Host basicHost = new BasicHost(ipv4Address);
            //Host decoratedHost = new DNSDecorator(new PortScanDecorator(basicHost));
            //Host decoratedHost = new DNSDecorator(new PingDecorator(basicHost));
            //HostWatcher scanHost = new PortScanDecorator(decoratedHost);

            //Host decoratedHost = HostFactory.getDecoratedHost(new String[]{"Ping", "DNS"}, ipv4Address, true);
            Host decoratedHost = HostFactory.getDecoratedHost(args, ipv4Address, true);
            HostWatcher scanHost = (HostWatcher) HostFactory.decorateHost("PortScan", decoratedHost);
            decoratedHost.register(scanHost);

            // Connect and demonstrate the capabilities
            decoratedHost.connect();
            System.out.println(decoratedHost.getDomainName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
