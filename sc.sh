#!/bin/bash
javac Main.java -d dest -Xlint:unchecked
cd dest
java Main
cd ..