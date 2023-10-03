// ping.cpp

#ifdef __APPLE__
// If the OS doesn't declare it, do it ourself (copy-pasted from GNU C Library, license: LGPL)
#      include <stdint.h>
struct icmphdr
{
    uint8_t type;           /* message type */
    uint8_t code;           /* type sub-code */
    uint16_t checksum;
    union
    {
        struct
        {
            uint16_t        id;
            uint16_t        sequence;
        } echo;                 /* echo datagram */
        uint32_t        gateway;        /* gateway address */
        struct
        {
            uint16_t        __unused;
            uint16_t        mtu;
        } frag;                 /* path mtu discovery */
        /*uint8_t reserved[4];*/
    } un;
};

       // Fix slightly changed names
#      define SOL_IP IPPROTO_IP

#endif

#include "ping.h"
#include <iostream>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <chrono>
#include <stdexcept>
#include <unistd.h>
#include <netinet/ip_icmp.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/time.h>
#include <netdb.h>

Ping::Ping(const std::string& host) : host_(host), averageTime_(0), reachable_(false) {
}

std::vector<std::string> Ping::sendPing(int count) {
    std::vector<std::string> results;

    struct addrinfo hints, *result, *rp;

    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_family = AF_INET; // IPv4
    hints.ai_socktype = SOCK_RAW;
    hints.ai_protocol = IPPROTO_ICMP;

    if (getaddrinfo(host_.c_str(), NULL, &hints, &result) != 0) {
        results.push_back("Error resolving host: " + host_);
        return results;
    }

    // Iterate over the list of IP addresses and ping each one
    for (rp = result; rp != NULL; rp = rp->ai_next) {
        struct sockaddr_in target_addr;
        struct icmphdr packet;
        struct timeval start_time, end_time, timeout, total_time;

        // Create a raw socket
        int sock = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);
        if (sock < 0) {
            perror("socket");
            continue;
        }

        memset(&target_addr, 0, sizeof(target_addr));
        target_addr.sin_family = AF_INET;
        target_addr.sin_addr = ((struct sockaddr_in*)rp->ai_addr)->sin_addr;

        // Prepare the ICMP Echo Request packet
        memset(&packet, 0, sizeof(packet));
        packet.type = ICMP_ECHO;
        packet.code = 0;
        packet.un.echo.id = getpid();
        packet.un.echo.sequence = 1;
        packet.checksum = 0;
        packet.checksum = htons(~(0xFFFF & (ICMP_ECHO << 8)));

        // Get the start time
        if (gettimeofday(&start_time, NULL) == -1) {
            perror("gettimeofday");
            close(sock);
            results.push_back("Error getting start time");
            continue;
        }

        // Send the ICMP Echo Request packet
        if (sendto(sock, &packet, sizeof(packet), 0, (struct sockaddr*)&target_addr, sizeof(target_addr)) == -1) {
            perror("sendto");
            close(sock);
            results.push_back("Error sending ICMP packet to " + std::string(inet_ntoa(target_addr.sin_addr)));
            continue;
        }

        // Set a timeout for the select() function
        timeout.tv_sec = 1;
        timeout.tv_usec = 0;

        // Wait for a response
        fd_set read_set;
        FD_ZERO(&read_set);
        FD_SET(sock, &read_set);

        if (select(sock + 1, &read_set, NULL, NULL, &timeout) == -1) {
            perror("select");
            close(sock);
            results.push_back("Error waiting for response from " + std::string(inet_ntoa(target_addr.sin_addr)));
            continue;
        }

        // Get the end time
        if (gettimeofday(&end_time, NULL) == -1) {
            perror("gettimeofday");
            close(sock);
            results.push_back("Error getting end time");
            continue;
        }

        // Calculate the round-trip time
        timersub(&end_time, &start_time, &total_time);
        double elapsed_time = total_time.tv_sec * 1000.0 + total_time.tv_usec / 1000.0;

        // Display the result
        std::string result_str = "ip:" + std::string(inet_ntoa(target_addr.sin_addr)) + "|tm:" + std::to_string(elapsed_time) + " ms";
        results.push_back(result_str);

        // Close the socket
        close(sock);
    }

    freeaddrinfo(result);

    return results;
}

double Ping::getAverageTime() const {
    return averageTime_;
}

bool Ping::isReachable() const {
    return reachable_;
}
