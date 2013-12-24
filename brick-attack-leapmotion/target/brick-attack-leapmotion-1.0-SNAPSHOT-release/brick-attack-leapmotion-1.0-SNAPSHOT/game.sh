#!/bin/sh

BASE=`dirname $0`
java -jar -Djava.library.path="$BASE/lib/" "$BASE/brick-attack-leapmotion-1.0-SNAPSHOT.jar" &
