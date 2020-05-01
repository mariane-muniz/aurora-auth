#!/bin/bash

echo "Starting configuration server...";
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )";

cd $DIR;

mvn clean package;

java -Xmx64m -Xss256k -jar $DIR/target/auth.jar &

echo -e
