#!/usr/bin/env bash

java -cp $(cat server/target/cp.txt) dundertext.server.DundertextMain
