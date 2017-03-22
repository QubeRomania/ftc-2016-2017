package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

public abstract class AutonomousOpMode extends LinearRobotOpMode {
    protected void initialize() {
        robot.initServos();
        robot.prepareFire(true);
        robot.initAllMotors();
        robot.initSensors();
    }

    /// Calibrates the gyroscope.
    protected void calibrateGyro() {
        setStatus("Started calibrating gyro.");
        update();

        robot.gyro.calibrate();

        idle();

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
        waitForMs(700);

        robot.grabBalls(true);
        waitForMs(200);

        robot.grabBalls(false);
        waitForMs(900);

        robot.grabBalls(true);
        waitForMs(400);

        robot.grabBalls(false);
        robot.fireBalls(false);

        setStatus("All balls fired.");
        update();
    }

    //viteza
    // TODO: set this to a bigger speed
    static final double BASE_SPEED = 2;
    static final double LEFT_PROP = 0.34375;
    static final double RIGHT_PROP = 0.46875;

    /// In centimeters.
    static final double ROTATIONS_FROM_CENTER_VORTEX = 1.45;

    //PID procentual
    static final double P = 35;
    static final double I = 5;
    static final double D = 70;

    static final double scale = 0.042;


    protected void goTowardsCenterVortex() {
        runtime.reset();
        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;

        robot.setDriveMotorsRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        robot.setDriveMotorTargetRotations(ROTATIONS_FROM_CENTER_VORTEX);
        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_TO_POSITION);


        do {
            double motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala((BASE_SPEED * LEFT_PROP) - motorCorrection, (BASE_SPEED * RIGHT_PROP) + motorCorrection);

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
        }
        while (robot.leftFrontMotor.getCurrentPosition() < robot.leftFrontMotor.getTargetPosition() - 50);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(200);
        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected void pressBeacon(BeaconColor wantedColor) {
        double THINKING_TIME = 2;

        runtime.reset();

        BeaconColor color = null;

        while (runtime.time() <= THINKING_TIME && color == null && opModeIsActive()) {

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                color = BeaconColor.RED;
            } else if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                color = BeaconColor.BLUE;
            }

            telemetry.addData("Color code", robot.colorSensorBeacon.argb());
            telemetry.addData("Red value", robot.colorSensorBeacon.red());
            telemetry.addData("Blue value", robot.colorSensorBeacon.blue());

            telemetry.update();
        }

        if (runtime.time() > THINKING_TIME) {
            telemetry.addData("Status", "Error: thinking time exceeded!");
            telemetry.update();

            return;
        }

        if (color == wantedColor) {
            robot.servoBeacon.setPosition(1);
        } else {
            robot.servoBeacon.setPosition(0);
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

    protected void goToBeacon(BeaconColor teamColor, double distanceFromBeacon) {
        robot.tractiuneIntegrala(0, 0);
        waitForMs(1000);
        double error = 0;
        double direction, angle;
        double lastError;
        while (robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= distanceFromBeacon && opModeIsActive()) {
            direction = teamColor == BeaconColor.RED ? 87 : -90;
            angle = robot.gyro.getIntegratedZValue();
            lastError = error;
            error = direction - angle;
            double motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;
            robot.tractiuneIntegrala(0.2 - motorCorrection, 0.2 + motorCorrection);
            setStatus("Approaching beacon");
            telemetry.addData("Distance ", robot.usdSensorFrontRight.getDistance(DistanceUnit.CM));
            update();
        }

        robot.tractiuneIntegrala(0, 0);
        waitForMs(200);
    }

    protected void goToSecondBeacon() {
        runtime.reset();
        robot.tractiuneIntegrala(1, 1);

        while (runtime.milliseconds() <= 200 && opModeIsActive()) {
            setStatus("Leaving white line of the first beacon.");
            update();
        }

        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;
        double powerLeft = BASE_SPEED * LEFT_PROP;
        double powerRight = BASE_SPEED * RIGHT_PROP;

        runtime.reset();

        while (opModeIsActive() && (robot.colorSensorLine.alpha() < 12 || runtime.milliseconds() < 1000)) {
            angle = robot.gyro.getIntegratedZValue();
            lastError = error;
            error = direction - angle;

            //PID
            double motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            if (runtime.milliseconds() < 1200)
                robot.tractiuneIntegrala(powerLeft - motorCorrection, powerRight + motorCorrection);
            else
                robot.tractiuneIntegrala((powerLeft - motorCorrection) / 10, (powerRight + motorCorrection) / 10);

            setStatus("Going to second beacon's line.");
            telemetry.addData("Alpha ", robot.colorSensorLine.alpha());
            update();
        }

        robot.tractiuneIntegrala(-0.2, -0.2);
        waitForMs(300);

        robot.tractiuneIntegrala(0, 0);
        waitForMs(200);

        setStatus("We found the line");
        update();
    }
}
