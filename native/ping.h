// ping.h
#ifndef PING_H
#define PING_H

#include <string>
#include <vector>

class Ping {
public:
    Ping(const std::string& host);
    std::vector<std::string> sendPing(int count = 4);
    double getAverageTime() const;
    bool isReachable() const;
private:
    std::string host_;
    double averageTime_;
    bool reachable_;
};

#endif // PING_H
