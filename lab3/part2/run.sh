#!/bin/bash

bash kill.sh

java TrafficSink &
java FIFOScheduler &
java TrafficGeneratorPoisson localhost 9 &
java TrafficGeneratorMovie localhost 9
