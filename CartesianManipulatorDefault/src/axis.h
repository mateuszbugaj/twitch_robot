#include <Arduino.h>
#include <manipulator_enums.h>
#include <AccelStepper.h>

class Axis
{
public:
    AccelStepper motor;
    MICROSTEPPING stepping;
    DRIVE_FACTOR drive_factor;

    Axis(int dirPin, int stePin, float speed, float acc, DRIVE_FACTOR drive_factor, MICROSTEPPING stepping)
    {
        this->drive_factor = drive_factor;
        this->stepping = stepping;

        motor = AccelStepper(AccelStepper::FULL2WIRE, dirPin, stePin);
        motor.setSpeed(calculateSteps(speed));
        motor.setAcceleration(calculateSteps(acc));
    }

    void moveTo(float point)
    {
        motor.moveTo(calculateSteps(point));
    }

    void run()
    {
        motor.run();
    }

    bool atTarget()
    {
        return motor.distanceToGo() == 0;
    }

    int calculateSteps(float dist)
    {
        return dist * drive_factor * stepping;
    }
};