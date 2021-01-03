#include <Arduino.h>
#include <AccelStepper.h>
#include <vector>
#include <axis.h>
#include <bits/stdc++.h>

#define AXIS_X_DIR 25
#define AXIS_X_STEP 26

const float MAX_TRAVEL_SPEED = 10;
const float ACCELERATION = 5;

Axis axisX(AXIS_X_DIR, AXIS_X_STEP, MAX_TRAVEL_SPEED, ACCELERATION, SCREW, QUATER);
float value = 20;

void interpreter(String command){
  Serial.println(command);
}

void setup()
{
  Serial.begin(115200);

  axisX.moveTo(value);
}

void loop()
{

  if(Serial.available()){
    interpreter(Serial.readString());
  }

  if (axisX.atTarget())
  {
    Serial.println("DONE");
    value = value * -1;
    axisX.moveTo(value);
  }

  axisX.run();
}