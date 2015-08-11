#!/bin/bash

mvn clean install -U
if [ $? -ne 0 ]; then
    echo "[ERROR] MAVEN BUILD FAILURE"
    exit 1
fi

WAR=`find . -name "*.jar"`
java -jar $WAR
