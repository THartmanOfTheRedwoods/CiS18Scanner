// pingTest.cpp
#include "ping.h"
#include <iostream>

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <hostname>" << std::endl;
        return 1;
    }

    std::string host = argv[1];

    Ping ping(host);
    std::vector<std::string> results = ping.sendPing();

    for (const std::string& result : results) {
        std::cout << result << std::endl;
    }

    return 0;
}
