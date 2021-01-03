#include <Arduino.h>
#include <manipulator_enums.h>
#include <AccelStepper.h>

class Axis
{
public:
    AccelStepper motor;
    MICROSTEPPING stepping;
    DRIVE_FACTOR drive_factor;
    float MIN, MAX;

    Axis(int dirPin, int stePin, float speed, float acc, DRIVE_FACTOR drive_factor, MICROSTEPPING stepping, float MIN, float MAX)
    {
        this->drive_factor = drive_factor;
        this->stepping = stepping;
        this->MIN = MIN;
        this->MAX = MAX;

        motor = AccelStepper(AccelStepper::FULL2WIRE, dirPin, stePin);
        motor.setMaxSpeed(calculateSteps(speed));
        motor.setAcceleration(calculateSteps(acc));
    }

    void moveTo(float point)
    {
        if(point < MIN) {
            point = MIN;
        }

        if(point > MAX){
            point = MAX;
        }

        motor.moveTo(calculateSteps(point));
    }

    void setSpeed(float value){
        motor.setSpeed(calculateSteps(value));
    }

    void run()
    {
        motor.run();
    }

    bool atTarget()
    {
        return motor.distanceToGo() == 0;
    }

    int calculateSteps(float mm)
    {
        return mm * drive_factor * stepping;
    }

    float calculateMM(int steps){
        return steps/drive_factor/stepping;
    }

    float currentPosition(){
        return calculateMM(motor.currentPosition());
    }
};