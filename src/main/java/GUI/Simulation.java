package GUI;

import Utils.RobotPoseSubscriber;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Simulation implements RobotPoseSubscriber {
    static PApplet p;
    private final PShape base;
    private final PShape xAxis;
    private final PShape yAxis;
    private final PShape zAxis;
    private PVector robotPose;

    public Simulation() {
        base = p.loadShape("3d_models/base.obj");
        xAxis = p.loadShape("3d_models/xAxis.obj");
        yAxis = p.loadShape("3d_models/yAxis.obj");
        zAxis = p.loadShape("3d_models/zAxis.obj");

        base.setFill(GUIConfig.simulationBaseColor);
        xAxis.setFill(GUIConfig.simulationXAxisColor);
        yAxis.setFill(GUIConfig.simulationYAxisColor);
        zAxis.setFill(GUIConfig.simulationZAxisColor);

        robotPose = new PVector(0, 0, 0);
    }

    public void show(int x, int y, float rotX, float rotY){
        p.lights();
        p.ambientLight(50, 50, 50);

        p.pushMatrix();
        p.translate(x, y, 200);
        p.rotateX(p.PI + rotX);
        p.rotateY(-rotY);
        p.scale(0.6f);

        // base
        p.pushMatrix();
        p.translate(0, 0, 0);
        p.shape(base);
        p.popMatrix();

        // X Axis
        p.pushMatrix();
        p.translate(robotPose.y, 0, robotPose.x);
        p.shape(xAxis);
        p.popMatrix();

        // Y Axis
        p.pushMatrix();
        p.translate(robotPose.y, 0, 0);
        p.shape(yAxis);
        p.popMatrix();

        // Z Axis
        p.pushMatrix();
        p.translate(robotPose.y,0,robotPose.x);
        p.shape(zAxis);
        p.popMatrix();

        p.popMatrix();
    }

    @Override
    public void updatePose(float x, float y, float z) {
        robotPose.set(x, y, z);
    }
}
