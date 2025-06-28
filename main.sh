#!/bin/bash
javac Main.java -d dest -Xlint:unchecked
cd dest
java -Xmx12g -Xms12g Main 
cd ..