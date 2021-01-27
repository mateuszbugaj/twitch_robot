# Twitch bot as an IRL robot interface

## Table of contents
1. [Introduction](#introduction)
2. [Technologies](#technologies)
3. [Options](#options)
4. [Launch](#launch)
5. [Cartesian manipulator controller](CartesianManipulatorDefault)

## Introduction <a name="introduction"></a>
This is an app that allows users from around the globe to control a cartesian manipulator using popular streaming service 
Twitch.tv via a channel's chat using basic commands. Commands are displayed in 'console' stylised on BASH in view. 
There are windows for displaying instructions and tips, programmed path and real time simulation of  the robot
updated using messages from the actual robot which is equipped with a webcam to allow users see results.
<br>
It's build with maven following the TDD approach using mockito and junit for testing.

![GUI_demo](https://imgur.com/fAMnI8x.jpg)

## Technologies <a name="technologies"></a>
- Java 11
- mockito / junit
- Maven
- Processing core library
- twitch4j
- jSerialComm

## Options <a name="options"></a>

- Choose WebCam device and COM port
```
Available webcam devices: 
Press enter to omit.
(0) HP Webcam
Type number of choice: 

Available serial ports: 
No option to choose from.
```
- Input commends directly (without Twitch chat)<br>
These commands will always appear at the top of the queue, but not before each other.
```
Type in admin commands: 
!x10; y20
!x30 z10
```
Output (at top):
```
root: x10
      y20
      x30 z10
```

- Ban users from inputting commands
```
Type in admin commands: 
ban user1
User user1 banned for this session.
```  

- Grand individual control over the robot for certain time (TODO)

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
java -jar target/twitch_robot-1.0.jar
```

Expected output: 
```
.../twitch_robot$ java -jar target/twitch_robot-1.0.jar
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

