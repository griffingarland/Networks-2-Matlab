#!/bin/bash

ps -ef | grep -v vim | grep java | grep -v grep | grep garland1 | tr -s ' ' | cut -d ' ' -f 2 | xargs kill -9
