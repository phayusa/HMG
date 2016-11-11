#!/bin/bash
rm -rf beans; mkdir beans
find . -name "*.java" > sourcefiles
javac -s src/ -d beans/ @sourcefiles
rm sourcefiles
