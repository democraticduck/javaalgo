#!/bin/bash
javac Main.java -d dest -Xlint:unchecked
cd dest
java Main -Xmx9g -Xms9g
cd ..