#!/bin/bash
javac DataGenerator.java -d dest -Xlint:unchecked
cd dest
java DataGenerator -Xmx12g -Xms12g
cd ..