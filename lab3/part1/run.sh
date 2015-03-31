#!/bin/bash

ps -ef | grep java | grep garland1 | grep -v grep | tr -s ' ' | cut -d' ' -f2 | xargs kill -9

rm output.txt
rm sink.txt
rm scheduler.txt

java TrafficSink &
java FIFOScheduler &
java TrafficGenerator_2 localhost 5 1 &
