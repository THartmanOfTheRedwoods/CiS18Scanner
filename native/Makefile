# Makefile for Java_edu_redwoods_cis18_sectools_PingDecorator_pingHost

# Compiler
CC = g++

# Compiler flags
## CFLAGS = -Wall -shared -fPIC -I/opt/graalvm/include -I/opt/graalvm/include/linux
CFLAGS = -Wall -shared -fPIC -I/Users/trevorhartman/Library/Java/JavaVirtualMachines/openjdk-20.0.2/Contents/Home/include -I/Users/trevorhartman/Library/Java/JavaVirtualMachines/openjdk-20.0.2/Contents/Home/include/darwin
LDFLAGS = -shared

# Source files
SRC = edu_redwoods_cis18_sectools_PingDecorator.cpp ping.cpp

# Output shared library name
LIBRARY = libpingjni.dylib

all: $(LIBRARY)

$(LIBRARY): $(SRC)
	$(CC) $(CFLAGS) $(SRC) -o $(LIBRARY) $(LDFLAGS)

clean:
	rm -f $(LIBRARY)

.PHONY: all clean