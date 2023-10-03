// PingJNI.cpp
#include "edu_redwoods_cis18_sectools_PingDecorator.h"
#include "ping.h"
#include <iostream>
#include <vector>

JNIEXPORT void JNICALL Java_edu_redwoods_cis18_sectools_PingDecorator_pingHost(JNIEnv* env, jobject obj, jstring javaHost, jobjectArray javaResults) {
    // Convert the Java string to a C++ string
    const char* host = env->GetStringUTFChars(javaHost, nullptr);
    if (host == nullptr) {
        return; // Error handling
    }

    // Create a Ping object and send ping
    Ping ping(host);
    std::vector<std::string> results = ping.sendPing();

    // Release the host string
    env->ReleaseStringUTFChars(javaHost, host);

    // Convert and set the ping results as Java strings
    for (size_t i = 0; i < results.size(); i++) {
        jstring javaResult = env->NewStringUTF(results[i].c_str());
        env->SetObjectArrayElement(javaResults, i, javaResult);
        env->DeleteLocalRef(javaResult);
    }
}
