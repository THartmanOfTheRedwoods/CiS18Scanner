package edu.redwoods.cis18.sectools;

import java.util.Arrays;

public class PingDecorator extends HostDecorator {
    static {
        System.loadLibrary("pingjni"); // Load the shared library
    }

    private String[] pingResults;
    public PingDecorator(Host host) {
        super(host);
        this.pingResults = new String[10];  // I don't expect any more than 10 results.
    }

    @Override
    public void connect() {
        super.connect();
        addPingResolution();
    }

    private native void pingHost(String host, String[] results);

    private void addPingResolution() {
        System.out.println("Adding Host Ping capability...");
        this.pingHost(this.host.getDomainName(), this.pingResults);
        if(Arrays.stream(this.pingResults).findAny().isPresent()) {
            notifyWatchers();
        }
        // Adding some printing just for fun.
        for (String result : this.pingResults) {
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
