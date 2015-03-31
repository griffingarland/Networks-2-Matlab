#!/bin/bash

bash kill.sh

java TrafficSink &
java FIFOScheduler &
java TrafficGeneratorPoisson localhost 1 &
java TrafficGeneratorMovie localhost 1 
