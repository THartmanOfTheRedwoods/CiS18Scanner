package edu.redwoods.cis18.sectools;

public class PortScanDecorator extends HostDecorator implements HostWatcher{
    public PortScanDecorator(Host host) {
        super(host);
    }

    @Override
    public void connect() {
        super.connect();
        addPortScanning();
    }

    private void addPortScanning() {
        System.out.println("Adding Port scanning capability...");
    }

    @Override
    public void update() {
        if(this.host == null) {
            System.out.println("WHY IS THIS NULL");
        }
        System.out.printf("Starting Port Scan of %s%n", this.host.getDomainName());
    }

    @Override
    public void setHost(Host host) {
        this.host = host;
    }
}