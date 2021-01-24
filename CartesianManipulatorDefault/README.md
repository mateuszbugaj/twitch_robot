# Cartesian manipulator controller

## Table of contents
1. [Schematic](#schematic)
2. [Technologies](#technologies)
3. [Options](#options)

## Schematic
![Schematic](https://imgur.com/EIWrxhR.jpg)

## Technologies
- ESP32 / Arduino microcontroller
- AccelStepper library (Stepper motor control)
- PlatformIO (Building and deployment)

## Options
Connects via usb with baud rate of 115200 and send command lines.<br>
Each command can start with the letter `a` to specify `absolute` position. Absence will be interpreted as `relative` position.<br>
Command can have optionally value for each axis written as the letter denoting axis and value in millimeters without space in between.<br>
Here are examples:
- `x10 y10` (Move 10 mm on X axis and Y axis) <br>
- `y10 z15` (Move 5 mm on Y and 15 mm on Z axis)<br>
- `a x-10 y20` (Move to the point (-10, 20, 0))<br>

Programmatically there is option to change `DRIVE_FACTOR` which describes gear ratio on the stepper motor or maximum speed.