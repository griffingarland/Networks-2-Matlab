#!/bin/bash

ps -ef | grep -v vim | grep java | grep garland1 | grep -v grep | tr -s ' ' | cut -d' ' -f2 | xargs kill -9

rm output.txt
rm sink.txt
rm scheduler.txt
rm scheduler1.txt
rm scheduler2.txt
rm scheduler3.txt

java TrafficSink &
java FIFOScheduler &
java TrafficGeneratorPoisson localhost 4 1 &
java TrafficGeneratorPoisson localhost 4 2 &
java TrafficGeneratorPoisson localhost 4 3 &
