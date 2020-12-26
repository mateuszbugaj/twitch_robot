# twitch_robot - Twitch bot as irl robot interface

## Table of contents
1. [Introduction](#introduction)
2. [Technologies](#technologies)
3. [Options](#options)
4. [Launch](#launch)

## Introduction <a name="introduction"></a>
The aim of this project is to create a desktop app that allows twitch users to control a cartesian manipulator 
via a channel's chat using basic commands. These commands are displayed in command line interface stylised on 
BASH. There are windows for displaying instructions and tips, programmed path and real time simulation of  the robot
updated using messages from the actual robot which is equipped with a web cam to allow users see results.
<br>
It's build with maven following the TDD approach using mockito and junit for testing.
![GUI_demo](https://imgur.com/mSnVwVw.jpg)

## Technologies <a name="technologies"></a>
- Java 11
- Processing core library
- twitch4j
- jSerialComm
- mockito / junit
- Maven

## Options <a name="options"></a>

## Launch <a name="launch"></a>
Tested with AdoptOpenJDK 11 (There might be an error running on other java distributions) <br>
Because there is no official maven repositories for some of graphical libraries, in order to launch this project
you need to add some local dependencies. Go into root directory and run:
```
mvn install:install-file -Dfile=lib/processing-core.jar -DgroupId=org.processing -DartifactId=core -Dversion=3.7.7 -Dpackaging=jar
mvn install:install-file -Dfile=lib/video.jar -DgroupId=org.processing -DartifactId=video -Dversion=3.7.7 -Dpackaging=jar
mvn install:install-file -Dfile=lib/gst1-java-core-1.2.0.jar -DgroupId=org.freedesktop -DartifactId=gstreamer -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/jna.jar -DgroupId=org.sun -DartifactId=jna -Dversion=1.0 -Dpackaging=jar
```

Other dependencies should be downloaded and added automatically. To start project run:
```
mvn clean package
java -cp target/twitch_robot-1.0.jar TwitchRobot
```

Expected output: 
```
.../twitch_robot$ java -cp target/twitch_robot-1.0.jar TwitchRobot
  ______           __       __
 /_  __/      __ _/ /______/ /_
  / / | | /| / / / __/ ___/ __ \
 / /  | |/ |/ / / /_/ /__/ / / /
/_/   |__/|__/_/\__/\___/_/_/_/ 
   _________  / /_  ____  / /_  
  / ___/ __ \/ __ \/ __ \/ __/  
 / /  / /_/ / /_/ / /_/ / /_    
/_/   \____/_____/\____/\__/
```
The app is now running.

