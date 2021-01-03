#include <Arduino.h>
#include <vector>
#include <axis.h>
#include <interpreter.h>
#include <bits/stdc++.h>

// PINOUT
#define AXIS_X_DIR 25
#define AXIS_X_STEP 26

#define AXIS_Y_DIR 27
#define AXIS_Y_STEP 14

#define AXIS_Z_DIR 18
#define AXIS_Z_STEP 5

#define ANALOG_X 34 // X axis joystick
#define ANALOG_Y 35 // Y axis joystick

// constants
const float MAX_TRAVEL_SPEED = 10;
const float ACCELERATION = 5;

const float MIN_Y = -100;
const float MAX_Y = 100;
const float MIN_X = -120;
const float MAX_X = 120;
const float MIN_Z = 0;
const float MAX_Z = 100;

Axis axisX(AXIS_X_DIR, AXIS_X_STEP, MAX_TRAVEL_SPEED, ACCELERATION, SCREW, QUATER, MIN_X, MAX_X);
Axis axisY(AXIS_Y_DIR, AXIS_Y_STEP, MAX_TRAVEL_SPEED, ACCELERATION, BELT, QUATER, MIN_Y, MAX_Y);
Axis axisZ(AXIS_Z_DIR, AXIS_Z_STEP, MAX_TRAVEL_SPEED, ACCELERATION, SCREW, QUATER, MIN_Z, MAX_Z);

void setup()
{
  Serial.begin(115200);
  analogRead(ANALOG_X);
  analogRead(ANALOG_Y);
}

bool doneSent = true;
bool analogXMoved = false;
bool analogYMoved = false;
float analogX, analogY;

void loop()
{

  // Serial com control support
  if (axisX.atTarget() && axisY.atTarget() && axisZ.atTarget())
  {
    if (doneSent == false)
    {
      Serial.printf("x%.0f y%.0f z%.0f DONE\n", axisX.currentPosition(), axisY.currentPosition(), axisZ.currentPosition());
      doneSent = true;
    }

    if (Serial.available())
    {
      String command = Serial.readString();
      std::vector<float> values = interpret(command, axisX.currentPosition(), axisY.currentPosition(), axisZ.currentPosition());

      axisX.moveTo(values[0]);
      axisY.moveTo(values[1]);
      axisZ.moveTo(values[2]);

      doneSent = false;
    }
  } 

  // Joystick control support
  analogX = static_cast<int>(analogRead(ANALOG_X) / 100) - 18;
  if (abs(analogX) > 3)
  {
    axisX.setSpeed(analogX);
    axisX.motor.runSpeed();
    axisX.motor.moveTo(axisX.motor.currentPosition());

    analogXMoved = true;
  }
  else if (analogXMoved)
  {
    axisX.setSpeed(0);
    analogXMoved = false;
  }
  else
  {
    axisX.run();
  }

  analogY = static_cast<int>(analogRead(ANALOG_Y) / 100) - 18;
  if (abs(analogY) > 3)
  {
    axisY.setSpeed(analogY);
    axisY.motor.runSpeed();
    axisY.motor.moveTo(axisY.motor.currentPosition());

    analogYMoved = true;
  }
  else if (analogYMoved)
  {
    axisY.setSpeed(0);
    analogYMoved = false;
  }
  else
  {
    axisY.run();
  }

  axisZ.run();
}