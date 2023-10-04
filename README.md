# CiS18Scanner

Demonstration of utilizing 3 design patterns to implement a skeleton scanner:

* Factory Design Pattern for creating Decorated Hosts
* Decorator Pattern for decorating a BasicHost with
    * DNSDecorator
    * PingDecorator
    * PortScanDecorator
* Observer Pattern for observing Host changes and reacting to them.
    * The Host is the Observer Subject
    * The HostWatcher Interface is the Observer Interface
    * Decorated Hosts like PortScanner and DNSDecorator can also be HostWatcher Observers to Observer other Host changes.
        * For example consider the PortScannerDecorator watching a PingDecorator Host so it can get notified if PingDecorator returns results for an active host.
