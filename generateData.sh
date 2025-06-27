#!/bin/bash
javac Main.java -d dest -Xlint:unchecked
cd dest
java Main -Xmx7g -Xms7g
cd ..