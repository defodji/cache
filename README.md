# N-way set associative

## Overview

The purpose of this library is to provide an implementation of an n-way set associative cache in Java. The cache itself is entirely in memory. To create a new cache, the size (N) of the set, the number of sets and the cache replacement algorithm are needed.
You can also add your custom cache replacement algorithm by creating an implementation of the interface CacheReplacementAlgorithm in the package com.exercise.cache.replacementalgorithm.

## Build/setup/execution

* To build the project you can go in the folder of the project (folder named <cache>) and type the following command (maven should be installed on the computer):

mvn clean package 

* Then move into the target folder where a JAR executable has been created and launch the execution of the JAR file :

cd target
java -jar Cache-0.0.1.jar <SetCount> <SetSize> <ReplacementAlgorithm>

where <SetCount> is the number of sets, <SetSize> the maximum size of a set and <ReplacementAlgorithm> the cache replacement algorithm (MRU or LRU are the allowed values).

For instance for an 2-way set associative with MRU replacement algorithm and 2 sets, you can type:

java -jar Cache-0.0.1.jar 2 2 MRU

A client is automatically executed and fill and overflow the cache with values. It then shows the content of the cache so one can see the values that have been replaced (the NULL values).
 