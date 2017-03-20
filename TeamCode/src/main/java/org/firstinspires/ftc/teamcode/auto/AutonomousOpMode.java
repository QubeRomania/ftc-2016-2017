package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

public abstract class AutonomousOpMode extends LinearRobotOpMode {
    /// Calibrates the gyroscope.
    protected void calibrateGyro() {
        setStatus("Started calibrating gyro.");
        update();

        robot.gyro.calibrate();

        while (robot.gyro.isCalibrating() && !opModeIsActive()) {
            setStatus("Calibrating...");
            update();

            idle();
        }

        if (isStarted()) {
            setStatus("Error: gyro NOT calibrated!");
            update();

            requestOpModeStop();
            return;
        }

        setStatus("Done calibrating gyro.");
        update();
    }

    protected void fireAllParticles() {
        setStatus("Launching particles...");
        update();

        robot.fireBalls(true);

        robot.prepareFire(true);

        waitForMs(700);

        robot.grabBalls(true);
        waitForMs(250);

        robot.grabBalls(false);
        waitForMs(900);

        robot.grabBalls(true);
        waitForMs(250);

        robot.grabBalls(false);
        robot.prepareFire(false);
        robot.fireBalls(false);

        setStatus("All balls fired.");
        update();
    }

    private void addDistanceData() {
        telemetry.addData("Left front distance", "%f cm", leftFrontDistance);
        telemetry.addData("Right front distance", "%f cm", rightFrontDistance);
        telemetry.update();
    }

    private double leftFrontDistance = 0, rightFrontDistance = 0;

    //viteza
    // TODO: set this to a bigger speed
    static final double BASE_SPEED = 1.0;
    static final double LEFT_PROP = 0.34375;
    static final double RIGHT_PROP = 0.46875;

    /// In centimeters.
    static final double DISTANCE_FROM_CENTER_VORTEX = 65;

    //PID procentual
    static final double P = 30;
    static final double I = 10;
    static final double D = 60;

    static final double scale = 0.05;


    protected void goTowardsCenterVortex() {
        runtime.reset();
        time = runtime.time();

        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;

        addDistanceData();


        do {
            leftFrontDistance = robot.usdSensorFrontLeft.getDistance(DistanceUnit.CM);
            rightFrontDistance = robot.usdSensorFrontRight.getDistance(DistanceUnit.CM);


            double motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala((BASE_SPEED * LEFT_PROP) - motorCorrection, (BASE_SPEED * RIGHT_PROP) + motorCorrection);
            addDistanceData();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
        }
        while (leftFrontDistance >= DISTANCE_FROM_CENTER_VORTEX && rightFrontDistance >= DISTANCE_FROM_CENTER_VORTEX);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(600);
    }

    protected void pressBeacon(BeaconColor wantedColor) {
        double THINKING_TIME = 2;

        runtime.reset();

        BeaconColor color = null;

        while (runtime.time() <= THINKING_TIME && color == null && opModeIsActive()) {

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                color = BeaconColor.RED;
            }
            else if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                color = BeaconColor.BLUE;
            }

            telemetry.addData("Color code", robot.colorSensorBeacon.argb());
            telemetry.addData("Red value", robot.colorSensorBeacon.red());
            telemetry.addData("Blue value", robot.colorSensorBeacon.blue());

            telemetry.update();
        }

        if (runtime.time() > THINKING_TIME)
        {
            telemetry.addData("Status", "Error: thinking time exceeded!");
            telemetry.update();

            return;
        }

        if (color == wantedColor) {
            robot.servoBeacon.setPosition(0);
        } else {
            robot.servoBeacon.setPosition(1);
        }

        waitForMs(500);

        telemetry.addData("Beacon color", "The color is %s", color);
        telemetry.update();

        robot.tractiuneIntegrala(0.6, 0.6);

        waitForMs(500);

        robot.tractiuneIntegrala(-0.6, -0.6);

        waitForMs(300);

        robot.tractiuneIntegrala(0, 0);
    }
}
