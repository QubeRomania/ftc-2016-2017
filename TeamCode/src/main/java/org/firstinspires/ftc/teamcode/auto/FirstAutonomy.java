/*package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

@Autonomous(name = "First autonomy", group = "Autonomy")
public final class FirstAutonomy extends LinearRobotOpMode {

    public static final double THINKING_TIME = 2.0;
    static final double LEFT_BASE_SPEED = 0.4;
    static final double RIGHT_BASE_SPEED = 0.5;

    public double time;
    public double error;
    public double direction = 0;
    public double angle;
    public double distance = 40;

    public String culoare = null;

    private void initialize() {
        robot.initServos();
        robot.initAllMotors();
        robot.initSensors();
    }

    private void calibrateGyro() {
        robot.gyro.calibrate();

        while (robot.gyro.isCalibrating()){
            setStatus("Calibrating...");
            update();
            idle();
        }

        setStatus("Done calibrating gyro!");
        update();
    }

    private void goTowardsCenterVortex() {
        runtime.reset();

        time = runtime.time();

        while(runtime.time() - time <= 1.3) {
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
            robot.tractiuneIntegrala(LEFT_BASE_SPEED - (error / 50), RIGHT_BASE_SPEED + (error / 50));

            telemetry.addData("Gyro Z value", robot.gyro.getIntegratedZValue());
            telemetry.update();
        }

        robot.tractiuneIntegrala(0, 0);
    }

    private void fireAllParticles() {
        robot.prepareFire(true);
        robot.fireBalls(true);
        sleep(700);
        robot.grabBalls(true);
        sleep(500);
        robot.grabBalls(false);
        sleep(500);
        robot.grabBalls(true);
        sleep(5000);
        robot.grabBalls(false);
        robot.prepareFire(false);
        robot.fireBalls(false);
    }

    @Override
    public void play() {
        // Init all subsystems.
        initialize();

        // Calibrate the gyro.
        calibrateGyro();

        waitForStart();

        // Reach the firing position.
        goTowardsCenterVortex();

        fireAllParticles();

        direction = 60;
        error = 0;
        time = runtime.time();

        while(runtime.time() - time <= 4 && robot.lightSensorLeft.getLightDetected() < 0.2){
            telemetry.addData("lumina", robot.lightSensorLeft.getLightDetected());
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
            telemetry.addData("unghi", angle);
            telemetry.addData("eroare", error);
            robot.tractiuneIntegrala(LEFT_BASE_SPEED -(error / 50), RIGHT_BASE_SPEED + (error / 50));

            telemetry.addData("Cautam", "linia");
            telemetry.update();
        }

        robot.tractiuneIntegrala(0.2, 0.2);
        sleep(500);
        robot.tractiuneIntegrala(0, 0);

        direction = 0;
        while (robot.gyro.getIntegratedZValue() >= 15){
            robot.tractiuneIntegrala(0.78, -0.78);
        }

        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

        while (robot.colorSensorLine.argb() < 5){
            robot.tractiuneIntegrala(-0.2, -0.2);
        }

        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

        while (robot.gyro.getIntegratedZValue() < 75){
            robot.tractiuneIntegrala(-0.78, 0.78);
        }

        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

        direction = 0;
        while(robot.gyro.getIntegratedZValue() != 0){
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
            robot.tractiuneIntegrala(LEFT_BASE_SPEED -(error / 50), RIGHT_BASE_SPEED + (error / 50));
        }
        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

        while (robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 15){
            robot.tractiuneIntegrala(0.2, 0.3);
        }
        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

        time = runtime.time();

        while (runtime.time() - time <= THINKING_TIME && culoare == null) {

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                telemetry.addData("rosu", robot.colorSensorBeacon.red());
                telemetry.update();
                culoare = "rosu";
                robot.servoBeacon.setPosition(1);
            }
            if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                telemetry.addData("albastru", robot.colorSensorBeacon);
                telemetry.update();
                culoare = "albastru";
                robot.servoBeacon.setPosition(0);

            }
        }

        sleep(1000);

        telemetry.addData("Status", "Culoarea este ", culoare);
        telemetry.update();

        while(robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 10){
            robot.tractiuneIntegrala(0.4, 0.4);
        }
        robot.tractiuneIntegrala(0, 0);
        sleep(1000);


        robot.tractiuneIntegrala(-0.2, -0.2);

        sleep(500);

        direction = 0;
        while (robot.gyro.getIntegratedZValue() >= 10){
            robot.tractiuneIntegrala(0.78, -0.78);
        }


        robot.tractiuneIntegrala(0, 0);
        sleep(500);


        direction = 0;
        time = runtime.time();
        while(robot.colorSensorLine.argb() < 8 || runtime.time() - time < 2){
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
            error += robot.usdSensorRightLeft.getDistance(DistanceUnit.CM) - distance;
            robot.tractiuneIntegrala(LEFT_BASE_SPEED - (error / 50), RIGHT_BASE_SPEED + (error / 50));
        }


        direction = 90;
        error = 90;
        while( Math.abs(error)  >= 10) {
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
            robot.tractiuneIntegrala(-0.78, 0.78);
        }
        robot.tractiuneIntegrala(0, 0);
        sleep(1000);


        time = runtime.time();

        while (runtime.time() - time <= THINKING_TIME && culoare == null) {

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                telemetry.addData("rosu", robot.colorSensorBeacon.red());
                telemetry.update();
                culoare = "rosu";
                robot.servoBeacon.setPosition(1);
            }
            if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                telemetry.addData("albastru", robot.colorSensorBeacon);
                telemetry.update();
                culoare = "albastru";
                robot.servoBeacon.setPosition(0);

            }
        }

        sleep(1000);

        telemetry.addData("Status", "Culoarea este ", culoare);
        telemetry.update();

        while(robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 10){
            robot.tractiuneIntegrala(0.4, 0.4);
        }

        robot.tractiuneIntegrala(0, 0);
        sleep(1000);

    }
}*/